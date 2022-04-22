package com.oumenreame.viewpager.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.task.DownLoad;

public class DownloadFragment extends Fragment {

    private static final String TAG = "3";
    private ProgressBar progressBar;
    private TextView mTvProgress;
    final String M_PROGRESS = "Progress";


    public DownloadFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_3, container, false);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
        outState.putInt(M_PROGRESS,progressBar.getProgress());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");

        EditText mEdtUrl = view.findViewById(R.id.edtUrl);
        String url = "http://54.39.180.249/";
        mEdtUrl.setText(url);
        Button mBtnDownload = view.findViewById(R.id.btnDownload);
        mBtnDownload.setOnClickListener(view1 -> {
            DownloadVideo downloadVideo = new DownloadVideo(view);
            downloadVideo.execute(url,"video.mp4");
        });
        mTvProgress = view.findViewById(R.id.tvProgress);
        progressBar = view.findViewById(R.id.progressBar);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, "onDetach: ");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        Log.e(TAG, "onViewStateRestored: ");
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadVideo extends DownLoad {

        View view;

        public DownloadVideo(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setProgress(0);
            mTvProgress.setText("0%");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Snackbar.make(view,"DownLoad Successful", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(view,"DownLoad Failed", Snackbar.LENGTH_LONG).show();
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setMax(100);
            progressBar.setProgress(values[0]);
            mTvProgress.setText(values[0]+"%");
        }
    }
}