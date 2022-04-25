package com.oumenreame.viewpager.fragment;

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
import com.oumenreame.viewpager.task.DownloadHandler;
import com.oumenreame.viewpager.task.DownloadThread;


public class HandlerFragment extends Fragment {

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
        Button mBtnDownload = view.findViewById(R.id.btnDownload);
        TextView mTvProgress = view.findViewById(R.id.tvProgress);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        TextView mTvProgress1 = view.findViewById(R.id.tvProgress1);
        ProgressBar progressBar1 = view.findViewById(R.id.progressBar1);
        TextView mTvProgress2 = view.findViewById(R.id.tvProgress2);
        ProgressBar progressBar2 = view.findViewById(R.id.progressBar2);

        mBtnDownload.setOnClickListener((view1 -> {
            DownloadHandler handler = new DownloadHandler(progressBar, mTvProgress, view, "A");
            DownloadHandler handler1 = new DownloadHandler(progressBar1, mTvProgress1, view, "B");
            DownloadHandler handler2 = new DownloadHandler(progressBar2, mTvProgress2, view, "C");

            new Thread(new DownloadThread(handler,500)).start();
            new Thread(new DownloadThread(handler1,800)).start();
            new Thread(new DownloadThread(handler2,1000)).start();
        }));
    }
}