package com.ownourome.musicmp3.ui.main;

import static com.ownourome.musicmp3.utils.Constant.NAME_FOLDER;
import static com.ownourome.musicmp3.utils.Constant.PERMISSIONS_MAIN;
import static com.ownourome.musicmp3.utils.Constant.READ_STORAGE;
import static com.ownourome.musicmp3.utils.Constant.SUB_FILE;
import static com.ownourome.musicmp3.utils.Constant.WRITE_STORAGE;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ownourome.musicmp3.R;
import com.ownourome.musicmp3.data.models.Song;
import com.ownourome.musicmp3.data.network.response.Result;
import com.ownourome.musicmp3.data.souce.Repository;
import com.ownourome.musicmp3.receiver.CheckConnectInternet;
import com.ownourome.musicmp3.ui.main.adapter.SongAdapter;
import com.ownourome.musicmp3.utils.Constant;
import com.ownourome.musicmp3.utils.callback.CheckInternetCallback;
import com.ownourome.musicmp3.utils.callback.RemoteCallback;
import com.ownourome.musicmp3.utils.callback.SongItemClick;
import com.ownourome.musicmp3.utils.permistion.RequiredPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private final HashMap<Long, Object[]> songDownloading = new HashMap<>();
    private SongAdapter mSongAdapter;
    private Repository repository;
    private ArrayList<Song> mSongs = new ArrayList<>();
    private BroadcastReceiver receiverInternet;
    private List<String> songFavorite;
    private List<String> songDownload;
    private TextView tvNoInternet;
    private final BroadcastReceiver receiverDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if (songDownloading.containsKey(id)) {
                Object[] value = songDownloading.get(id);
                if (value != null) {
                    int position = (int) value[0];
                    Song song = (Song) value[1];
                    if (song != null) {
                        song.setDownload(true);
                        if (songFavorite.contains(song.getId())) {
                            song.setFavorite(true);
                            repository.updateSong(song);
                        } else {
                            repository.insertSong(song);
                        }
                        songDownload.add(song.getId());
                    }
                    mSongAdapter.setSongDownload(songDownload);
                    mSongAdapter.notifyItemChanged(position);
                    songDownloading.remove(id);
                }
            }
        }
    };
    private RequiredPermission requiredPermission;
    private final ActivityResultLauncher<String[]> mResultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                for (String key : result.keySet()) {
                    if (Boolean.FALSE.equals(result.get(key))) {
                        requiredPermission.showAlert();
                        return;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View root = findViewById(android.R.id.content).getRootView();
        CheckInternetCallback checkInternetCallback = new CheckInternetCallback() {
            @Override
            public void isOnline() {
                initData();
                mRecyclerView.setVisibility(View.VISIBLE);
                tvNoInternet.setVisibility(View.GONE);
            }

            @Override
            public void isOffline() {
                initDataOffline();
                Snackbar.make(root, R.string.no_internet,Snackbar.LENGTH_SHORT).show();
            }
        };

        receiverInternet = new CheckConnectInternet(checkInternetCallback);
        requiredPermission = new RequiredPermission(this, mResultLauncher);
        repository = Repository.getInstance(this);

        requirePermissions();
        initViews();
        initListeners();
    }

    private void initDataOffline() {
        mSongs = (ArrayList<Song>) repository.getAllSongDownload();
        songFavorite = repository.getSongFavorite();
        songDownload = repository.getSongDownload();
        mSongAdapter.updateAdapter(mSongs, songFavorite, songDownload);

        if (mSongs.size() == 0) {
            mRecyclerView.setVisibility(View.GONE);
            tvNoInternet.setVisibility(View.VISIBLE);
        }
    }

    private void requirePermissions() {
        if (requiredPermission.isPermissioned(READ_STORAGE)
                || requiredPermission.isPermissioned(WRITE_STORAGE)) {
            requiredPermission.requestPermission(PERMISSIONS_MAIN);
        }
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.recyclerView);
        tvNoInternet = findViewById(R.id.tvNoInternet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initListeners() {
        SongItemClick mSongItemClick = new SongItemClick() {
            @Override
            public void itemClick(int position) {

            }

            @Override
            public void imgBtnFavoriteClick(Song song) {
                if (song.isFavorite()) {
                    song.setFavorite(false);
                    if (songDownload.contains(song.getId())) {
                        repository.updateSong(song);
                    } else {
                        repository.deleteSong(song);
                    }
                    songFavorite.remove(song.getId());
                } else {
                    song.setFavorite(true);
                    if (songDownload.contains(song.getId())) {
                        repository.updateSong(song);
                    } else {
                        repository.insertSong(song);
                    }

                    songFavorite.add(song.getId());
                }
                mSongAdapter.setSongFavorite(songFavorite);
            }

            @Override
            public void imgBtnDownloadClick(ImageButton imgBtnDownload, int position) {

                if (requiredPermission.isPermissioned(READ_STORAGE)
                        || requiredPermission.isPermissioned(WRITE_STORAGE)) {
                    requiredPermission.requestPermission(PERMISSIONS_MAIN);
                } else {
                    downloadSong(position);
                    imgBtnDownload.setColorFilter(getResources().getColor(R.color.favorite));
                    imgBtnDownload.setEnabled(true);
                }
            }
        };

        mSongAdapter = new SongAdapter(
                MainActivity.this,
                mSongs,
                mSongItemClick
        );
        mRecyclerView.setAdapter(mSongAdapter);
    }

    private void downloadSong(int position) {
        Song song = mSongs.get(position);
        if (songDownloading.size() >= 5) {
            Toast.makeText(this, "Please waiting 1 minute", Toast.LENGTH_SHORT).show();
            return;
        }
        File dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), NAME_FOLDER);
        } else {
            dir = new File(Environment.getExternalStorageDirectory(), NAME_FOLDER);
        }

        if (!dir.exists()) {
            boolean mkdir = dir.mkdir();
        }

        File file = new File(dir.getAbsolutePath(), song.getTitle().replace(" ", "_") + SUB_FILE);

        song.setLocation(
                Uri.fromFile(file).toString()
        );

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(song.getLinkSong()))
                .setTitle(getString(R.string.download_song))
                .setDescription(song.getTitle())
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationUri(Uri.fromFile(file))
                .setAllowedOverRoaming(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            request.setRequiresCharging(false).setAllowedOverMetered(true);
        }

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        long idDownload = downloadManager.enqueue(request);
        Object[] objects = new Object[2];
        objects[0] = position;
        objects[1] = song;
        songDownloading.put(idDownload, objects);
    }

    private void initData() {
        RemoteCallback getSongVNCallBack = new RemoteCallback() {
            @Override
            public void onSuccess(Result result) {
                if (result.getMsg().equals(Constant.VALUE_SUCCESS)) {
                    songFavorite = repository.getSongFavorite();
                    songDownload = repository.getSongDownload();
                    mSongs = result.getData().getSongs();
                    mSongAdapter.updateAdapter(mSongs, songFavorite, songDownload);
                } else {
                    Toast.makeText(MainActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(String messenger) {
                Toast.makeText(MainActivity.this, messenger, Toast.LENGTH_SHORT).show();
            }
        };

        repository.getSongVN(getSongVNCallBack);
    }



    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiverInternet, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(receiverDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiverInternet);
        unregisterReceiver(receiverDownloadComplete);
    }
}