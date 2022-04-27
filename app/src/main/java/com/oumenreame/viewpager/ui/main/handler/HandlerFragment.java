package com.oumenreame.viewpager.ui.main.handler;

import static com.oumenreame.viewpager.utils.Constant.PERMISSION;
import static com.oumenreame.viewpager.utils.Constant.URL_DOWNLOAD;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.service.DownloadHandler;
import com.oumenreame.viewpager.service.DownloadThread;


public class HandlerFragment extends Fragment {

    Button mBtnDownload;
    TextView mTvProgress;
    ProgressBar progressBar;
    TextView mTvProgress1;
    ProgressBar progressBar1;
    TextView mTvProgress2;
    ProgressBar progressBar2;

    ActivityResultLauncher<String[]> activityResultLauncher;

    public HandlerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_handler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initListener(view);

    }

    private void initListener(View view) {
        mBtnDownload.setOnClickListener((view1 -> {
            activityResultLauncher.launch(PERMISSION);
//            DownloadHandler handler = new DownloadHandler(progressBar, mTvProgress, view, "A");
//            DownloadHandler handler1 = new DownloadHandler(progressBar1, mTvProgress1, view, "B");
////            DownloadHandler handler2 = new DownloadHandler(progressBar2, mTvProgress2, view, "C");
//
//            new Thread(new DownloadThread(handler, 500,URL_DOWNLOAD,"video.mp4")).start();
//            new Thread(new DownloadThread(handler1, 800,URL_DOWNLOAD,"video1.mp4")).start();
//            new Thread(new DownloadThread(handler2, 1000,URL_DOWNLOAD,"video2.mp4")).start();
        }));
    }

    private void initView(View view) {
        mBtnDownload = view.findViewById(R.id.btnDownload);
        mTvProgress = view.findViewById(R.id.tvProgress);
        progressBar = view.findViewById(R.id.progressBar);
        mTvProgress1 = view.findViewById(R.id.tvProgress1);
        progressBar1 = view.findViewById(R.id.progressBar1);
        mTvProgress2 = view.findViewById(R.id.tvProgress2);
        progressBar2 = view.findViewById(R.id.progressBar2);
    }
}