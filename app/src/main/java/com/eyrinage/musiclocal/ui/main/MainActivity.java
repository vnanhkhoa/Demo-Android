package com.eyrinage.musiclocal.ui.main;

import static com.eyrinage.musiclocal.utils.Constant.ACTION_MUSIC;
import static com.eyrinage.musiclocal.utils.Constant.ACTION_PLAY;
import static com.eyrinage.musiclocal.utils.Constant.CLEAR;
import static com.eyrinage.musiclocal.utils.Constant.FAVORITE;
import static com.eyrinage.musiclocal.utils.Constant.FAVORITE_KEY;
import static com.eyrinage.musiclocal.utils.Constant.NEXT;
import static com.eyrinage.musiclocal.utils.Constant.PAUSE;
import static com.eyrinage.musiclocal.utils.Constant.PERMISSIONS_MAIN;
import static com.eyrinage.musiclocal.utils.Constant.PLAY;
import static com.eyrinage.musiclocal.utils.Constant.POSITION;
import static com.eyrinage.musiclocal.utils.Constant.PREVIOUS;
import static com.eyrinage.musiclocal.utils.Constant.READ_STORAGE;
import static com.eyrinage.musiclocal.utils.Constant.WRITE_STORAGE;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eyrinage.musiclocal.R;
import com.eyrinage.musiclocal.data.local.SongRepositoryImp;
import com.eyrinage.musiclocal.data.models.Song;
import com.eyrinage.musiclocal.service.permistion.RequiredPermission;
import com.eyrinage.musiclocal.service.service.PlayMusicService;
import com.eyrinage.musiclocal.ui.main.adapter.SongAdapter;
import com.eyrinage.musiclocal.utils.calback.ItemSongListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static HashMap<Long, Boolean> sHashMap;

    private final int FIRST_SONG = 0;
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int action = intent.getIntExtra(ACTION_PLAY, 0);
            switch (action) {
                case PLAY:
                    mImgBtnPlay.setImageResource(R.drawable.ic_round_pause_24);
                    break;
                case PAUSE:
                    mImgBtnPlay.setImageResource(R.drawable.ic_round_play_arrow_24);
                    break;
                case NEXT:
                    handleNext();
                    break;
                case PREVIOUS:
                    handlePrevious();
                    break;
                case CLEAR:
                    unbindService(mServiceConnection);
                    mLnPlay.setVisibility(View.GONE);
                    isConnect = false;
                    break;
            }
        }
    };
    private int lastSong = 0;
    private RecyclerView mRecyclerViewSong;
    private LinearLayout mLnPlay;
    private TextView mTvTitle;
    private TextView mTvArtist;
    private ImageButton mImgBtnPlay;
    private ImageButton mImgBtnNext;
    private ImageButton mImgBtnPrevious;
    private ImageView mImgAlbum;
    private SharedPreferences mSharedPreferences;
    private RequiredPermission requiredPermission;
    private SongRepositoryImp mSongRepositoryImp;
    private ArrayList<Song> mSongs;
    private final ActivityResultLauncher<String[]> mResultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                for (String key : result.keySet()) {
                    if (!result.get(key)) {
                        requiredPermission.showAlert();
                        return;
                    }
                }
//                mSongs = mSongRepositoryImp.getSongFromLocal();
                mSongRepositoryImp.getSongFolderAsset();
                mSongs = mSongRepositoryImp.getSongs();
            }
    );
    private Gson mGson;
    private ItemSongListener mItemSongListener;
    private boolean isConnect = false;
    private PlayMusicService mPlayMusicService;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayMusicService.LocalBinder localBinder = (PlayMusicService.LocalBinder) iBinder;
            mPlayMusicService = localBinder.getService();
            isConnect = true;

            position = mPlayMusicService.getPosition();
            setSong();
            if (mPlayMusicService.isPlaying()) {

            }
            mLnPlay.setVisibility(View.VISIBLE);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isConnect = false;
        }
    };
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requiredPermission = new RequiredPermission(this, mResultLauncher);
        mSongRepositoryImp = new SongRepositoryImp(this);
        mSharedPreferences = getSharedPreferences(FAVORITE_KEY, Context.MODE_PRIVATE);
        mGson = new Gson();

        setDataLocal();

        initView();
        initListener();

    }

    private void setDataLocal() {
        getsHashMap();
        if (requiredPermission.isPermissioned(WRITE_STORAGE) ||
                requiredPermission.isPermissioned(READ_STORAGE)) {
            requiredPermission.requestPermission(PERMISSIONS_MAIN);
            mSongs = new ArrayList<>();
        } else {
//            mSongs = mSongRepositoryImp.getSongFromLocal();
            mSongRepositoryImp.getSongFolderAsset();
            mSongs = mSongRepositoryImp.getSongs();
            if (isServiceRunning(MainActivity.this)) {
                Intent intent = new Intent(MainActivity.this, PlayMusicService.class);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
            }
        }
    }


    private void initView() {
        mRecyclerViewSong = findViewById(R.id.recyclerViewSong);
        mLnPlay = findViewById(R.id.lnPlay);
        mTvTitle = findViewById(R.id.tvTitle);
        mTvArtist = findViewById(R.id.tvArtist);
        mImgBtnPlay = findViewById(R.id.imgBtnPlay);
        mImgBtnNext = findViewById(R.id.imgBtnNext);
        mImgBtnPrevious = findViewById(R.id.imgBtnPrevious);
        mImgAlbum = findViewById(R.id.imgAlbum);
    }

    private void setSong() {
        Song song = mSongs.get(position);
        mTvTitle.setText(song.getTitle());
        mTvArtist.setText(song.getArtist());

        if (song.getArtAlbum() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(song.getArtAlbum(), 0, song.getArtAlbum().length);
            mImgAlbum.setImageBitmap(bitmap);
        } else {
            mImgAlbum.setImageResource(R.drawable.ic_round_music_note_24);
        }

    }

    public void getsHashMap() {
        String json = mSharedPreferences.getString(FAVORITE, "");
        sHashMap = new HashMap<>();
        if (!json.equals("null")) {
            Type type = new TypeToken<HashMap<Long, Boolean>>() {
            }.getType();
            sHashMap = mGson.fromJson(json, type);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = mSharedPreferences.edit();
        mGson = new Gson();
        String toJson = mGson.toJson(sHashMap);
        editor.putString(FAVORITE, toJson);
        editor.apply();

    }

    private void initListener() {
        mItemSongListener = new ItemSongListener() {
            @Override
            public void onItemClick(int position) {
                playSong(position);
                setSong();
            }

            @Override
            public void onImageButtonPlayClick(int position) {
                playSong(position);
                setSong();
            }

            @Override
            public void onImageButtonFavoriteClick(ImageButton imageButton, int position) {
                Song song = mSongs.get(position);
                if (song.isFavorite()) {
                    sHashMap.remove(song.get_id());
                    imageButton.setImageResource(R.drawable.ic_round_favorite_border_24);
                } else {
                    sHashMap.put(song.get_id(), true);
                    imageButton.setImageResource(R.drawable.ic_round_favorite_24);
                }
                song.setFavorite(!song.isFavorite());
            }
        };

        mImgBtnPlay.setOnClickListener((view -> handlePlayAndPause()));
        mImgBtnPrevious.setOnClickListener(view -> {
            if (!isConnect) return;
            mPlayMusicService.handlePrevious();
            handlePrevious();
        });
        mImgBtnNext.setOnClickListener(view -> {
            if (!isConnect) return;
            mPlayMusicService.handleNext();
            handleNext();
        });
    }

    private void handleNext() {
        lastSong = mSongs.size() - 1;
        if (position == lastSong) {
            position = FIRST_SONG;
        } else {
            position += 1;
        }
        setSong();
    }

    private void handlePrevious() {
        lastSong = mSongs.size() - 1;
        if (position == FIRST_SONG) {
            position = lastSong;
        } else {
            position -= 1;
        }
        setSong();
    }

    private void handlePlayAndPause() {
        if (!isConnect) return;
        if (mPlayMusicService.isPlaying()) {
            mPlayMusicService.pause();
            mImgBtnPlay.setImageResource(R.drawable.ic_round_play_arrow_24);
        } else {
            mPlayMusicService.resume();
            mImgBtnPlay.setImageResource(R.drawable.ic_round_pause_24);
        }
    }

    private void playSong(int position) {
        this.position = position;

        Intent intent = new Intent(MainActivity.this, PlayMusicService.class);
        intent.putExtra(POSITION,position);
        startService(intent);

        if (!isConnect) {
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
        mImgBtnPlay.setImageResource(R.drawable.ic_round_pause_24);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerViewSong.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerViewSong.setAdapter(
                new SongAdapter(
                        mSongs,
                        MainActivity.this,
                        mItemSongListener
                )
        );

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_MUSIC);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    public boolean isServiceRunning(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (PlayMusicService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}