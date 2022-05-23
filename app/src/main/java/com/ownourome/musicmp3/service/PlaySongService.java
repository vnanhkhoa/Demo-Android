package com.ownourome.musicmp3.service;

import static com.ownourome.musicmp3.utils.Constant.ACTION_INTENT_MUSIC;
import static com.ownourome.musicmp3.utils.Constant.ACTION_PLAY;
import static com.ownourome.musicmp3.utils.Constant.CHANNEL_ID;
import static com.ownourome.musicmp3.utils.Constant.CLEAR;
import static com.ownourome.musicmp3.utils.Constant.NEXT;
import static com.ownourome.musicmp3.utils.Constant.NOTIFICATION_ID;
import static com.ownourome.musicmp3.utils.Constant.PAUSE;
import static com.ownourome.musicmp3.utils.Constant.PLAY;
import static com.ownourome.musicmp3.utils.Constant.POSITION;
import static com.ownourome.musicmp3.utils.Constant.PREVIOUS;
import static com.ownourome.musicmp3.utils.Constant.SONG_DATA_EXTRA;
import static com.ownourome.musicmp3.utils.Constant.SONG_ID;
import static com.ownourome.musicmp3.utils.Constant.SONG_LIST;
import static com.ownourome.musicmp3.utils.Constant.START_ANIMATE;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ownourome.musicmp3.R;
import com.ownourome.musicmp3.data.models.Song;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PlaySongService extends Service {
    public static final String TAG = "TAG_NOTIFICATION";
    private final IBinder binder = new LocalBinder();
    private MediaPlayer mMediaPlayer;
    private Song song;
    private int mPosition;
    private ArrayList<Song> mSongs;
    private Gson mGson;
    private Type type;
    private boolean isPlaying = false;
    private int duration;

    public PlaySongService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGson = new Gson();
        type = new TypeToken<ArrayList<Song>>() {
        }.getType();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            if (intent.hasExtra(SONG_DATA_EXTRA)) {
                Bundle bundle = intent.getBundleExtra(SONG_DATA_EXTRA);
                if (song == null || !bundle.getString(SONG_ID).equals(song.getId())) {
                    this.mPosition = bundle.getInt(POSITION);
                    String jsonSongs = bundle.getString(SONG_LIST);
                    this.mSongs = mGson.fromJson(jsonSongs, type);
                    setMediaPlayer();
                    sendNotification();
                    sendActionBroadcast(START_ANIMATE);
                }
            }

            if (intent.getAction() != null && intent.getAction().equals(ACTION_INTENT_MUSIC)) {
                int action = intent.getIntExtra(ACTION_PLAY, 0);
                handleReceiver(action);
            }
        }

        return START_STICKY;
    }

    private void sendActionBroadcast(int action) {
        Intent intent = new Intent(ACTION_INTENT_MUSIC);
        intent.putExtra(ACTION_PLAY, action);
        sendBroadcast(intent);
    }

    private void handleReceiver(int action) {
        sendActionBroadcast(action);
        switch (action) {
            case PLAY:
                resume();
                break;
            case PAUSE:
                pause();
                break;
            case CLEAR:
                stopForeground(true);
                mMediaPlayer.release();
                mMediaPlayer = null;
                isPlaying = false;
                break;
            case NEXT:
                handleNext();
                break;
            case PREVIOUS:
                handlePrevious();
                break;
        }
    }

    public void handleNext() {
        int lastSong = mSongs.size() - 1;
        if (mPosition == lastSong) {
            this.mPosition = 0;
        } else {
            this.mPosition += 1;
        }
        setMediaPlayer();
        sendNotification();
    }

    public void handlePrevious() {
        int lastSong = mSongs.size() - 1;
        if (mPosition == 0) {
            mPosition = lastSong;
        } else {
            mPosition -= 1;
        }
        setMediaPlayer();
        sendNotification();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void setMediaPlayer() {
        this.song = mSongs.get(mPosition);
        if (mMediaPlayer != null) mMediaPlayer.release();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mMediaPlayer.setDataSource(song.getLinkSong());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnPreparedListener(mp -> {
            mp.start();
            isPlaying = true;
            duration = mp.getDuration();
        });

        mMediaPlayer.setOnCompletionListener(mp -> {
            handleNext();
            sendActionBroadcast(NEXT);
        });
    }

    private void sendNotification() {
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, TAG);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        Glide.with(this)
                .asBitmap()
                .load(song.getThumbnail())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        PendingIntent previous = setPendingIntent(PREVIOUS);
                        PendingIntent next = setPendingIntent(NEXT);
                        PendingIntent clear = setPendingIntent(CLEAR);

                        if (isPlaying) {
                            PendingIntent pause = setPendingIntent(PAUSE);
                            builder
                                    .addAction(R.drawable.ic_round_skip_previous_24, getString(R.string.Previous), previous)
                                    .addAction(R.drawable.ic_round_pause_24, getString(R.string.Pause), pause)
                                    .addAction(R.drawable.ic_round_skip_next_24, getString(R.string.Next), next);
                        } else {
                            PendingIntent play = setPendingIntent(PLAY);
                            builder
                                    .addAction(R.drawable.ic_round_skip_previous_24, getString(R.string.Previous), previous)
                                    .addAction(R.drawable.ic_round_play_arrow_24, getString(R.string.Play), play)
                                    .addAction(R.drawable.ic_round_skip_next_24, getString(R.string.Next), next);
                        }

                        builder.setContentTitle(song.getTitle())
                                .setContentText(song.getArtistsNames())
                                .setSmallIcon(R.drawable.icon)
                                .setLargeIcon(resource)
                                .addAction(R.drawable.ic_round_close_24, getString(R.string.Clear), clear)
                                .setPriority(NotificationCompat.PRIORITY_MIN)
                                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                        .setShowActionsInCompactView(0, 1, 2)
                                        .setMediaSession(mediaSessionCompat.getSessionToken()));
                        startForeground(NOTIFICATION_ID, builder.build());
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }


    public int getDuration() {
        return duration;
    }

    public int getDurationCurrent() {
        if (mMediaPlayer == null) {
            return 0;
        } else {
            return mMediaPlayer.getCurrentPosition();
        }
    }

    public void setSeekBarMediaPlayer(int millisecond) {
        mMediaPlayer.seekTo(millisecond);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public Song getSong() {
        return this.song;
    }

    public void resume() {
        if (mMediaPlayer == null) {
            setMediaPlayer();
        } else {
            mMediaPlayer.start();
        }
        isPlaying = true;
        sendNotification();
    }

    public void pause() {
        if (mMediaPlayer == null) return;
        mMediaPlayer.pause();
        isPlaying = false;
        sendNotification();
    }

    @SuppressLint("InlinedApi")
    private PendingIntent setPendingIntent(int action) {
        Intent intent = new Intent(ACTION_INTENT_MUSIC);
        intent.putExtra(ACTION_PLAY, action);
        int flag;
        if (action == PAUSE || action == PLAY) {
            flag = PendingIntent.FLAG_MUTABLE;
        } else {
            flag = PendingIntent.FLAG_IMMUTABLE;
        }
        return PendingIntent.getService(this, action, intent, flag);
    }

    public class LocalBinder extends Binder {
        public PlaySongService getService() {
            return PlaySongService.this;
        }
    }

}