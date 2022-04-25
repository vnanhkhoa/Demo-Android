package com.oumenreame.viewpager.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.adapter.ListViewAdapter;
import com.oumenreame.viewpager.core.Data;
import com.oumenreame.viewpager.model.Model;

import java.util.ArrayList;

public class ListViewFragment extends Fragment {

    ListView mList;
    ArrayList<Model> mModels;
    private static final String TAG = "Show";


    public ListViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        mModels = Data.createModel(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview, container, false);
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mList = view.findViewById(R.id.list);
        ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(),R.layout.item,mModels);
        mList.setAdapter(listViewAdapter);
        Log.e(TAG, "onViewCreated: ");
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
}