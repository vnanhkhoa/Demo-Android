package com.oumenreame.viewpager.ui.main.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oumenreame.viewpager.ui.detailmodel.DetailModelActivity;
import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.ui.main.recyclerview.adapter.RecyclerviewAdapter;
import com.oumenreame.viewpager.utils.callback.ItemListenerCallback;
import com.oumenreame.viewpager.utils.Constant;
import com.oumenreame.viewpager.data.Data;
import com.oumenreame.viewpager.data.model.Item;

import java.util.ArrayList;
import java.util.Objects;


public class RecyclerViewFragment extends Fragment {

    private ArrayList<Item> mItems;
    private static final String TAG = "Home";
    private ItemListenerCallback mCallback;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    @SuppressLint("UseRequireInsteadOfGet")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        mItems = Data.createModel(Objects.requireNonNull(getContext()));
        mCallback = model -> {
            Intent intent = new Intent(getContext(), DetailModelActivity.class);
            intent.putExtra(Constant.MODEL,model);
            getContext().startActivity(intent);
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
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

        initView(view);

        Log.e(TAG, "onViewCreated: ");

    }

    private void initView(View view) {
        RecyclerView mRecycleView = view.findViewById(R.id.recycleView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerviewAdapter recyclerviewAdapter = new RecyclerviewAdapter(getContext(), mItems,mCallback);
        mRecycleView.setAdapter(recyclerviewAdapter);
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