package com.oumenreame.viewpager.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.adapter.RecyclerviewAdapter;
import com.oumenreame.viewpager.core.Data;
import com.oumenreame.viewpager.model.Model;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView ryl;
    private ArrayList<Model> models;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        models = Data.createModel(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ryl = view.findViewById(R.id.ryl);
        ryl.setLayoutManager(new LinearLayoutManager(getContext()));
        ryl.setAdapter(new RecyclerviewAdapter(getContext(),models));

        return view;
    }

}