package com.eyrinage.musiclocal.service.service;

import static com.eyrinage.musiclocal.utils.Constant.ACTION_MUSIC;
import static com.eyrinage.musiclocal.utils.Constant.ACTION_PLAY;
import static com.eyrinage.musiclocal.utils.Constant.CHANNEL_ID;
import static com.eyrinage.musiclocal.utils.Constant.CLEAR;
import static com.eyrinage.musiclocal.utils.Constant.NEXT;
import static com.eyrinage.musiclocal.utils.Constant.NOTIFICATION_ID;
import static com.eyrinage.musiclocal.utils.Constant.PAUSE;
import static com.eyrinage.musiclocal.utils.Constant.PLAY;
import static com.eyrinage.musiclocal.utils.Constant.POSITION;
import static com.eyrinage.musiclocal.utils.Constant.PREVIOUS;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.eyrinage.musiclocal.R;
import com.eyrinage.musiclocal.data.local.SongRepositoryImp;
import com.eyrinage.musiclocal.data.models.Song;

import java.io.IOException;
import java.util.ArrayList;

public class PlayMusicService extends Service {
    public static final String TAG = "TAG_NOTIFICATION";
    private final IBinder binder = new LocalBinder();
    MediaPlayer mediaPlayer;
    private Song song;
    private boolean isPlaying = false;
    private ArrayList<Song> songs;
    private int position;


    public PlayMusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        position = -1;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (intent.hasExtra(POSITION)) {
                SongRepositoryImp mSongRepositoryImp = new SongRepositoryImp(this);
//                songs = mSongRepositoryImp.getSongFromLocal();
                mSongRepositoryImp.getSongFolderAsset();
                songs = mSongRepositoryImp.getSongs();
                this.position = intent.getIntExtra(POSITION, -1);
                this.song = this.songs.get(position);

                setMediaPlayer();
                sendNotification();
            }

            if (intent.getAction() != null && intent.getAction().equals(ACTION_MUSIC)) {
                int action = intent.getIntExtra(ACTION_PLAY, 0);
                handleReceiver(action);
            }
        }
        return START_STICKY;
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void handleReceiver(int action) {
        Intent intent = new Intent(ACTION_MUSIC);
        intent.putExtra(ACTION_PLAY, action);
        sendBroadcast(intent);
        switch (action) {
            case PLAY:
                resume();
                break;
            case PAUSE:
                pause();
                break;
            case CLEAR:
                stopSelf();
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
        int lastSong = songs.size() - 1;
        if (position == lastSong) {
            this.position = 0;
        } else {
            this.position += 1;
        }
        setMediaPlayer();
        sendNotification();
    }

    public void handlePrevious() {
        int lastSong = songs.size() - 1;
        if (position == 0) {
            position = lastSong;
        } else {
            position -= 1;
        }
        setMediaPlayer();
        sendNotification();
    }

    private void sendNotification() {
        this.song = songs.get(position);
        Bitmap icon;
        if (song.getArtAlbum() != null) {
            icon = BitmapFactory.decodeByteArray(song.getArtAlbum(), 0, song.getArtAlbum().length);
        } else {
            icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.icon);
        }

        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, TAG);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID);

        PendingIntent previous = setPendingIntent(PREVIOUS);
        PendingIntent next = setPendingIntent(NEXT);
        PendingIntent clear = setPendingIntent(CLEAR);

        if (isPlaying) {
            PendingIntent pause = setPendingIntent(PAUSE);
            notification
                    .addAction(R.drawable.ic_round_skip_previous_24, "Previous", previous)
                    .addAction(R.drawable.ic_round_pause_24, "Pause", pause)
                    .addAction(R.drawable.ic_round_skip_next_24, "Next", next);
        } else {
            PendingIntent play = setPendingIntent(PLAY);
            notification
                    .addAction(R.drawable.ic_round_skip_previous_24, "Previous", previous)
                    .addAction(R.drawable.ic_round_play_arrow_24, "Play", play)
                    .addAction(R.drawable.ic_round_skip_next_24, "Next", next);
        }

        notification.setSubText(song.getAlbum())
                .setContentTitle(song.getTitle())
                .setContentText(song.getArtist())
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(icon)
                .addAction(R.drawable.ic_round_close_24, "Clear", clear)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.getSessionToken()));
        startForeground(NOTIFICATION_ID, notification.build());
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent setPendingIntent(int action) {
        Intent intent = new Intent(ACTION_MUSIC);
        intent.putExtra(ACTION_PLAY, action);
        int flag;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (action != PAUSE && action != PLAY) {
                flag = PendingIntent.FLAG_IMMUTABLE;
            } else {
                flag = PendingIntent.FLAG_MUTABLE;
            }
        } else  {
            if (action != PAUSE && action != PLAY) {
                flag = PendingIntent.FLAG_ONE_SHOT;
            } else {
                flag = PendingIntent.FLAG_UPDATE_CURRENT;
            }
        }
        return PendingIntent.getService(this, action, intent, flag);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer == null) return;
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void resume() {
        if (mediaPlayer == null) return;
        mediaPlayer.start();
        this.isPlaying = true;
        sendNotification();
    }

    public void pause() {
        if (mediaPlayer == null) return;
        mediaPlayer.pause();
        this.isPlaying = false;
        sendNotification();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public int getPosition() {
        return position;
    }

    private void setMediaPlayer() {
        this.song = songs.get(position);
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            AssetFileDescriptor afd = getAssets().openFd(song.getLocation());
            mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(mp -> Log.e(TAG, "sendNotification: " + mediaPlayer.getDuration() * 1000));

        this.isPlaying = true;
    }

    public class LocalBinder extends Binder {
        public PlayMusicService getService() {
            return PlayMusicService.this;
        }
    }
}