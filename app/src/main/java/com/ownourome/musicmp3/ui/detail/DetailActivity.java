package com.ownourome.musicmp3.ui.detail;

import static com.ownourome.musicmp3.ui.main.MainActivity.sRequiredPermission;
import static com.ownourome.musicmp3.ui.main.MainActivity.sSongDownload;
import static com.ownourome.musicmp3.ui.main.MainActivity.sSongFavorite;
import static com.ownourome.musicmp3.utils.Constant.ACTION_INTENT_MUSIC;
import static com.ownourome.musicmp3.utils.Constant.ACTION_PLAY;
import static com.ownourome.musicmp3.utils.Constant.CLEAR;
import static com.ownourome.musicmp3.utils.Constant.NAME_FOLDER;
import static com.ownourome.musicmp3.utils.Constant.NEXT;
import static com.ownourome.musicmp3.utils.Constant.ONE_SECOND;
import static com.ownourome.musicmp3.utils.Constant.PAUSE;
import static com.ownourome.musicmp3.utils.Constant.PERMISSIONS_MAIN;
import static com.ownourome.musicmp3.utils.Constant.PLAY;
import static com.ownourome.musicmp3.utils.Constant.PREVIOUS;
import static com.ownourome.musicmp3.utils.Constant.READ_STORAGE;
import static com.ownourome.musicmp3.utils.Constant.SUB_FILE;
import static com.ownourome.musicmp3.utils.Constant.TIME_START;
import static com.ownourome.musicmp3.utils.Constant.WRITE_STORAGE;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.ownourome.musicmp3.R;
import com.ownourome.musicmp3.data.models.Song;
import com.ownourome.musicmp3.data.souce.Repository;
import com.ownourome.musicmp3.service.PlaySongService;
import com.ownourome.musicmp3.ui.adapter.PageFragmentAdapter;
import com.ownourome.musicmp3.ui.detail.inforsong.InforSongFragment;
import com.ownourome.musicmp3.ui.detail.playsong.PlaySongFragment;

import java.io.File;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class DetailActivity extends AppCompatActivity {

    private final int MIN_PROGRESS = 0;
    public Song mSong;
    private final Handler mHandler = new Handler();
    private int duration;
    private ImageButton mImgBtnClose;
    private TextView mTvTitle;
    private TextView mTvArtist;
    private TextView mTvDurationStart;
    private TextView mTvDurationEnd;
    private SeekBar mSeekBarSong;
    private PageFragmentAdapter mPageFragmentAdapter;
    private ImageButton mImgBtnPrevious;
    private ImageButton mImgBtnPlay;
    private ImageButton mImgBtnNext;
    private ImageButton mImgBtnDownload;
    private CircularProgressIndicator mProgressDownload;
    private ImageButton mImgBtnFavorite;
    private Repository mRepository;
    private int colorFavorite;
    private int colorDownloaded;
    private int colorBlack;
    private boolean isConnect = false;
    private boolean isChange = false;
    private PlaySongService mPlaySongService;
    private InforSongFragment inforSongFragment;
    private PlaySongFragment playSongFragment;
    private long idDownload;
    private final BroadcastReceiver receiverDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (idDownload == id) {
                mProgressDownload.setIndeterminate(false);
                handleDownloadSuccess();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRepository = Repository.getInstance(this);

        colorFavorite = DetailActivity.this.getResources().getColor(R.color.favorite);
        colorDownloaded = DetailActivity.this.getResources().getColor(R.color.color_item);
        colorBlack = DetailActivity.this.getResources().getColor(R.color.black);

        initFragment();
        initData();
        initViews();
        initListener();
    }

    private void initFragment() {
        inforSongFragment = new InforSongFragment();
        playSongFragment = new PlaySongFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectBoundService();
    }

    private void updateDuration() {
        if (!isChange) {
            mSeekBarSong.setProgress(convertSecond());
        }
        mTvDurationStart.setText(mSong.getDurationString(mPlaySongService.getDurationCurrent()));
        mHandler.postDelayed(mRunnable, ONE_SECOND);
    }

    private void checkDownload() {
        if (mSong.isDownload()) {
            mImgBtnDownload.setEnabled(false);
            mImgBtnDownload.setColorFilter(colorDownloaded);
            int MAX_PROGRESS = 100;
            mProgressDownload.setProgress(MAX_PROGRESS);
        } else {
            mImgBtnDownload.setEnabled(true);
            mProgressDownload.setProgress(MIN_PROGRESS);
        }
    }

    private int convertSecond() {
        return Math.round(mPlaySongService.getDurationCurrent() * 1f / ONE_SECOND);
    }

    private void setInforSong() {
        duration = mPlaySongService.getDuration();
        mTvTitle.setText(mSong.getTitle());
        mTvArtist.setText(mSong.getArtistsNames());
        mTvDurationEnd.setText(mSong.getDurationString(duration));
        mSeekBarSong.setMax(duration / ONE_SECOND);
        if (!mPlaySongService.isPlaying()) {
            mImgBtnPlay.setImageResource(R.drawable.ic_round_play_arrow_24);
        } else {
            mImgBtnPlay.setImageResource(R.drawable.ic_round_pause_24);
        }
        checkFavorite();
        checkDownload();
    }

    private void checkFavorite() {
        if (mSong.isFavorite()) {
            mImgBtnFavorite.setColorFilter(colorFavorite);
        } else {
            mImgBtnFavorite.setColorFilter(colorBlack);
        }
    }

    private void connectBoundService() {
        Intent intent = new Intent(DetailActivity.this, PlaySongService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        mImgBtnClose.setOnClickListener(v -> finish());
        mImgBtnPlay.setOnClickListener(v -> handlePlayAndPause());
        mImgBtnFavorite.setOnClickListener(v -> handleFavorite());
        mImgBtnNext.setOnClickListener(v -> handleNext());
        mImgBtnPrevious.setOnClickListener(v -> handlePrevious());

        mSeekBarSong.setOnTouchListener((v, event) -> {
            SeekBar seekBar = (SeekBar) v;

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    isChange = true;
                    break;
                case MotionEvent.ACTION_UP:
                    mPlaySongService.setSeekBarMediaPlayer(seekBar.getProgress() * ONE_SECOND);
                    isChange = false;
                    break;
            }
            return false;
        });

        mImgBtnDownload.setOnClickListener(v -> {
            if (sRequiredPermission.isPermissioned(READ_STORAGE)
                    || sRequiredPermission.isPermissioned(WRITE_STORAGE)) {
                sRequiredPermission.requestPermission(PERMISSIONS_MAIN);
            } else {
                mImgBtnDownload.setEnabled(false);
                handleDownload();
            }
        });
    }

    private File checkVersionSaveDir() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), NAME_FOLDER);
        } else {
            return new File(Environment.getExternalStorageDirectory(), NAME_FOLDER);
        }
    }

    private void handleDownloadSuccess() {
        sSongDownload.add(mSong.getId());
        checkDownload();
        if (sSongFavorite.contains(mSong.getId())) {
            mRepository.updateSong(mSong);
        } else {
            mRepository.insertSong(mSong);
        }
        Toast.makeText(DetailActivity.this, "Download Success", Toast.LENGTH_SHORT).show();
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            int action = intent.getIntExtra(ACTION_PLAY, 0);
            switch (action) {
                case PLAY:
                    mImgBtnPlay.setImageResource(R.drawable.ic_round_pause_24);
                    break;
                case PAUSE:
                    mImgBtnPlay.setImageResource(R.drawable.ic_round_play_arrow_24);
                    break;
                case NEXT:
                case PREVIOUS:
                    mHandler.removeCallbacks(mRunnable);
                    handleChangeSong();
                    break;
                case CLEAR:
                    unbindService(mServiceConnection);
                    isConnect = false;
                    mHandler.removeCallbacks(mRunnable);
                    mSeekBarSong.setProgress(MIN_PROGRESS);
                    mImgBtnPlay.setImageResource(R.drawable.ic_round_play_arrow_24);
                    mTvDurationStart.setText(TIME_START);
                    connectBoundService();
                    break;
            }
        }
    };

    private void handleDownload() {
        File dir = checkVersionSaveDir();
        if (!dir.exists()) {
            if (!dir.mkdirs()) return;
        }

        File file = new File(
                dir.getAbsolutePath(),
                mSong.getTitle().replace(" ", "_") + SUB_FILE
        );
        mSong.setLocation(Uri.fromFile(file).toString());

        if (file.exists()) {
            handleDownloadSuccess();
            return;
        }
        mProgressDownload.setIndeterminate(true);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mSong.getLinkSong()))
                .setTitle(getString(R.string.download_song) + " " + mSong.getTitle())
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(Uri.fromFile(file))
                .setAllowedOverRoaming(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            request.setRequiresCharging(false).setAllowedOverMetered(true);
        }

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        idDownload = downloadManager.enqueue(request);
    }

    private void handlePrevious() {
        if (!isConnect) return;
        mPlaySongService.handlePrevious();
        handleChangeSong();
    }

    private void handleNext() {
        if (!isConnect) return;
        mPlaySongService.handleNext();
        handleChangeSong();
    }

    private void handleChangeSong() {
        mSong = mPlaySongService.getSong();
        setInforSong();
        updateDuration();
        updateInforSong();
    }

    private void updateInforSong() {
        inforSongFragment.setInForSong(mSong);
        playSongFragment.setImgSong(mSong.getThumbnail());
    }

    private void handleFavorite() {
        if (mSong == null) return;
        if (mSong.isFavorite()) {
            mSong.setFavorite(false);
            sSongFavorite.remove(mSong.getId());
            if (sSongDownload.contains(mSong.getId())) {
                mRepository.updateSong(mSong);
            } else {
                mRepository.deleteSong(mSong);
            }
        } else {
            mSong.setFavorite(true);
            sSongFavorite.add(mSong.getId());
            if (sSongDownload.contains(mSong.getId())) {
                mRepository.updateSong(mSong);
            } else {
                mRepository.insertSong(mSong);
            }
        }

        checkFavorite();
    }

    private void handlePlayAndPause() {
        if (!isConnect) return;
        if (mPlaySongService.isPlaying()) {
            mPlaySongService.pause();
            mHandler.removeCallbacks(mRunnable);
            mImgBtnPlay.setImageResource(R.drawable.ic_round_play_arrow_24);
        } else {
            mPlaySongService.resume();
            mImgBtnPlay.setImageResource(R.drawable.ic_round_pause_24);
            updateDuration();
        }
    }

    private void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(inforSongFragment);
        fragments.add(playSongFragment);

        mPageFragmentAdapter = new PageFragmentAdapter(this, fragments);
    }

    private void initViews() {
        mImgBtnClose = findViewById(R.id.imgBtnClose);
        mTvTitle = findViewById(R.id.tvTitle);
        mTvArtist = findViewById(R.id.tvArtist);

        ViewPager2 mViewPagerInforSong = findViewById(R.id.viewPagerInforSong);
        mViewPagerInforSong.setAdapter(mPageFragmentAdapter);
        mViewPagerInforSong.setCurrentItem(1);
        CircleIndicator3 mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mViewPagerInforSong);

        mSeekBarSong = findViewById(R.id.seekBarSong);
        mTvDurationStart = findViewById(R.id.tvDurationStart);
        mTvDurationEnd = findViewById(R.id.tvDurationEnd);

        mImgBtnPrevious = findViewById(R.id.imgBtnPrevious);
        mImgBtnPlay = findViewById(R.id.imgBtnPlay);
        mImgBtnNext = findViewById(R.id.imgBtnNext);
        mImgBtnDownload = findViewById(R.id.imgDownload);
        mProgressDownload = findViewById(R.id.progressDownload);
        mImgBtnFavorite = findViewById(R.id.imgBtnFavorite);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_INTENT_MUSIC);
        registerReceiver(broadcastReceiver, intentFilter);
        registerReceiver(receiverDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(receiverDownloadComplete);
        mHandler.removeCallbacks(mRunnable);
    }

    private final Runnable mRunnable = this::updateDuration;


    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlaySongService.LocalBinder localBinder = (PlaySongService.LocalBinder) iBinder;
            mPlaySongService = localBinder.getService();
            isConnect = true;

            mSong = mPlaySongService.getSong();
            setInforSong();
            updateInforSong();
            if (mPlaySongService.isPlaying()) {
                updateDuration();
            } else {
                if (mPlaySongService.getDurationCurrent() != 0) {

                    mSeekBarSong.setProgress(convertSecond());
                    mTvDurationStart.setText(mSong.getDurationString(mPlaySongService.getDurationCurrent()));
                } else {
                    mSeekBarSong.setProgress(MIN_PROGRESS);
                    mTvDurationStart.setText(TIME_START);
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnect = false;
        }
    };

}