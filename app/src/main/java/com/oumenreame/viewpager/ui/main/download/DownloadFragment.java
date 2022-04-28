package com.oumenreame.viewpager.ui.main.download;

import static com.oumenreame.viewpager.ui.main.MainActivityViewPager2.requiredPermission;
import static com.oumenreame.viewpager.utils.Constant.READ_STORAGE;
import static com.oumenreame.viewpager.utils.Constant.URL_DOWNLOAD;
import static com.oumenreame.viewpager.utils.Constant.WRITE_STORAGE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.service.DownLoad;

public class DownloadFragment extends Fragment {

    private static final String TAG = "3";
    private Button mBtnDownload;
    private ProgressBar progressBar;
    private TextView mTvProgress;
    final String M_PROGRESS = "Progress";


    public DownloadFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_download, container, false);
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
        outState.putInt(M_PROGRESS, progressBar.getProgress());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "onViewCreated: ");

        initView(view);
        initListener(view);

    }

    private void initListener(View view) {
        mBtnDownload.setOnClickListener(view1 -> {
            if (requiredPermission.isPermissioned(READ_STORAGE) && requiredPermission.isPermissioned(WRITE_STORAGE)) {
                handleDownload(view);
            } else {
                requiredPermission.requestPermission(READ_STORAGE, WRITE_STORAGE);
            }
        });
    }

    private void handleDownload(View view) {
        DownloadVideo downloadVideo2 = new DownloadVideo(view, progressBar, mTvProgress, 1);
        downloadVideo2.execute(URL_DOWNLOAD, "video2.mp4");

        mBtnDownload.setEnabled(false);
    }

    private void initView(View view) {
        EditText mEdtUrl = view.findViewById(R.id.edtUrl);
        mEdtUrl.setText(URL_DOWNLOAD);
        mBtnDownload = view.findViewById(R.id.btnDownload);
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
        ProgressBar progressBar;
        TextView tv;
        int a;

        public DownloadVideo(View view, ProgressBar progressBar, TextView tv, int a) {
            this.view = view;
            this.progressBar = progressBar;
            this.tv = tv;
            this.a = a;

            Log.e(TAG, "DownloadVideo: " + a);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG, "onPreExecute: " + a);
            progressBar.setProgress(0);
            mTvProgress.setText("0%");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                Snackbar.make(view, "DownLoad Successful", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(view, "DownLoad Failed", Snackbar.LENGTH_LONG).show();
            }
            mBtnDownload.setEnabled(true);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.e(TAG, "onProgressUpdate: " + a);
            progressBar.setProgress(values[0]);
            tv.setText(values[0] + "%");
        }
    }
}