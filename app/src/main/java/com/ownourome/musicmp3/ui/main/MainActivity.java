package com.ownourome.musicmp3.ui.main;

import static com.ownourome.musicmp3.utils.Constant.ACTION_INTENT_MUSIC;
import static com.ownourome.musicmp3.utils.Constant.ACTION_PLAY;
import static com.ownourome.musicmp3.utils.Constant.CLEAR;
import static com.ownourome.musicmp3.utils.Constant.NEXT;
import static com.ownourome.musicmp3.utils.Constant.PAUSE;
import static com.ownourome.musicmp3.utils.Constant.PERMISSIONS_MAIN;
import static com.ownourome.musicmp3.utils.Constant.PLAY;
import static com.ownourome.musicmp3.utils.Constant.PREVIOUS;
import static com.ownourome.musicmp3.utils.Constant.READ_STORAGE;
import static com.ownourome.musicmp3.utils.Constant.START_ANIMATE;
import static com.ownourome.musicmp3.utils.Constant.TIME_DELAY;
import static com.ownourome.musicmp3.utils.Constant.WRITE_STORAGE;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ownourome.musicmp3.R;
import com.ownourome.musicmp3.data.models.Song;
import com.ownourome.musicmp3.data.souce.Repository;
import com.ownourome.musicmp3.service.PlaySongService;
import com.ownourome.musicmp3.ui.detail.DetailActivity;
import com.ownourome.musicmp3.ui.main.libary.LibraryFragment;
import com.ownourome.musicmp3.ui.main.rank.RankFragment;
import com.ownourome.musicmp3.utils.permistion.RequiredPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<String> sSongFavorite;
    public static List<String> sSongDownload;
    private static int pageCurrent = 0;

    private LinearLayout mLnPlay;
    private TextView mTvTitle;
    private TextView mTvArtist;
    private ImageView mImgThumbnail;
    private ImageButton mImgBtnPlay;
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mImgThumbnail.animate().rotationBy(360).withEndAction(this).setDuration(TIME_DELAY)
                    .setInterpolator(new LinearInterpolator()).start();
        }
    };
    @SuppressLint("StaticFieldLeak")
    public static RequiredPermission sRequiredPermission;
    public final ActivityResultLauncher<String[]> mResultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                for (String key : result.keySet()) {
                    if (Boolean.FALSE.equals(result.get(key))) {
                        sRequiredPermission.showAlert();
                        return;
                    }
                }
            });
    private BottomNavigationView mBottomNavigationView;
    private RankFragment mRankFragment;
    private LibraryFragment mLibraryFragment;
    private Repository mRepository;
    private ImageButton mImgBtnNext;
    private ImageButton mImgBtnPrevious;
    private boolean isConnect = false;
    private PlaySongService mPlaySongService;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlaySongService.LocalBinder localBinder = (PlaySongService.LocalBinder) iBinder;
            mPlaySongService = localBinder.getService();
            isConnect = true;

            if (mPlaySongService.isPlaying()) {
                mLnPlay.setVisibility(View.VISIBLE);
                setSongPlaying(mPlaySongService.getSong());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnect = false;
        }
    };
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int action = intent.getIntExtra(ACTION_PLAY, 0);
            switch (action) {
                case PLAY:
                    mImgBtnPlay.setImageResource(R.drawable.ic_round_pause_24);
                    startAnimate();
                    break;
                case PAUSE:
                    mImgBtnPlay.setImageResource(R.drawable.ic_round_play_arrow_24);
                    stopAnimate();
                    break;
                case NEXT:
                case PREVIOUS:
                    setSongPlaying(mPlaySongService.getSong());
                    break;
                case CLEAR:
                    unbindService(mServiceConnection);
                    isConnect = false;
                    stopAnimate();
                    mImgBtnPlay.setImageResource(R.drawable.ic_round_play_arrow_24);
                    connectBoundService();
                    break;
                case START_ANIMATE:
                    startAnimate();
                    break;
            }
        }
    };

    public void setSongPlaying(Song song) {
        if (song == null) return;
        mLnPlay.setVisibility(View.VISIBLE);
        mTvTitle.setText(song.getTitle());
        mTvArtist.setText(song.getArtistsNames());
        Glide.with(mLnPlay.getContext())
                .load(song.getThumbnail())
                .error(R.drawable.ic_round_music_note_24)
                .into(mImgThumbnail);
        mImgBtnPlay.setImageResource(R.drawable.ic_round_pause_24);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reloadPage();
    }

    private void reloadPage() {
        switch (pageCurrent) {
            case 0:
                mRankFragment.updateDataPage();
                break;
            case 1:
                mLibraryFragment.reloadPage();
                break;
            default:
                break;
        }
    }

    private void startAnimate() {
        mImgThumbnail
                .animate()
                .rotationBy(360)
                .withEndAction(runnable)
                .setDuration(TIME_DELAY)
                .setInterpolator(new LinearInterpolator())
                .start();
    }

    private void stopAnimate() {
        mImgThumbnail.animate().cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sRequiredPermission = new RequiredPermission(this, mResultLauncher);
        mRepository = Repository.getInstance(this);

        requirePermissions();
        initData();
        initViews();
        initFragment();
        initListener();
    }

    private void connectBoundService() {
        Intent intent = new Intent(MainActivity.this, PlaySongService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isConnect) {
            setSongPlaying(mPlaySongService.getSong());
            if (mPlaySongService.isPlaying()) {
                startAnimate();
            } else {
                mImgBtnPlay.setImageResource(R.drawable.ic_round_play_arrow_24);
            }
        } else {
            connectBoundService();
        }
    }

    private void initViews() {
        mBottomNavigationView = findViewById(R.id.bottomNavigation);
        mLnPlay = findViewById(R.id.lnPlay);
        mTvTitle = findViewById(R.id.tvTitle);
        mTvArtist = findViewById(R.id.tvArtist);
        mImgBtnPlay = findViewById(R.id.imgBtnPlay);
        mImgBtnNext = findViewById(R.id.imgBtnNext);
        mImgBtnPrevious = findViewById(R.id.imgBtnPrevious);
        mImgThumbnail = findViewById(R.id.imgThumbnail);

    }

    public void setFragment(Fragment fragment, boolean isInit) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        if (!isInit) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.commit();
    }

    private void requirePermissions() {
        if (sRequiredPermission.isPermissioned(READ_STORAGE)
                || sRequiredPermission.isPermissioned(WRITE_STORAGE)) {
            sRequiredPermission.requestPermission(PERMISSIONS_MAIN);
        }
    }

    private void initData() {
        sSongDownload = mRepository.getSongDownload();
        sSongFavorite = mRepository.getSongFavorite();
    }

    private void initFragment() {
        mRankFragment = new RankFragment();
        mLibraryFragment = new LibraryFragment();
        setFragment(mRankFragment, true);
    }

    @SuppressLint("NonConstantResourceId")
    private void initListener() {
        mBottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.rank:
                    setFragment(mRankFragment, false);
                    pageCurrent = 0;
                    return true;
                case R.id.library:
                    setFragment(mLibraryFragment, false);
                    pageCurrent = 1;
                    return true;
                default:
                    return false;
            }


        });

        mImgBtnPlay.setOnClickListener((v) -> handlePlayAndPause());

        mImgBtnNext.setOnClickListener((v) -> handleNext());

        mImgBtnPrevious.setOnClickListener((v) -> handlePrevious());
        mLnPlay.setOnClickListener(v -> handleClickPlaySong());
    }

    private void handleClickPlaySong() {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }

    private void handlePrevious() {
        mPlaySongService.handlePrevious();
        setSongPlaying(mPlaySongService.getSong());
    }

    private void handleNext() {
        mPlaySongService.handleNext();
        setSongPlaying(mPlaySongService.getSong());
    }

    private void handlePlayAndPause() {
        if (!isConnect) return;
        if (mPlaySongService.isPlaying()) {
            mPlaySongService.pause();
            stopAnimate();
            mImgBtnPlay.setImageResource(R.drawable.ic_round_play_arrow_24);
        } else {
            mPlaySongService.resume();
            startAnimate();
            mImgBtnPlay.setImageResource(R.drawable.ic_round_pause_24);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_INTENT_MUSIC);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
        stopAnimate();
    }
}