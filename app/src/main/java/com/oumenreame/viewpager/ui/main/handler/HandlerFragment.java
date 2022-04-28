package com.oumenreame.viewpager.ui.main.handler;

import static com.oumenreame.viewpager.utils.Constant.URL_DOWNLOAD;

import android.os.Bundle;
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
            DownloadHandler handler = new DownloadHandler(progressBar, mTvProgress, view, "A",mBtnDownload);
            new Thread(new DownloadThread(handler, 500,URL_DOWNLOAD,"video.mp4")).start();
        }));
    }

    private void initView(View view) {
        mBtnDownload = view.findViewById(R.id.btnDownload);
        mTvProgress = view.findViewById(R.id.tvProgress);
        progressBar = view.findViewById(R.id.progressBar);
    }
}