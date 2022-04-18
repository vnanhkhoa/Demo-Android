package com.oumenreame.viewpager.fragment;

import static com.oumenreame.viewpager.core.Data.dpToPx;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.adapter.ListViewAdapter;
import com.oumenreame.viewpager.adapter.RecyclerviewAdapter;
import com.oumenreame.viewpager.core.Data;
import com.oumenreame.viewpager.model.Model;

import java.util.ArrayList;

public class ShowFragment extends Fragment {

    ListView list;
    ArrayList<Model> models;


    public ShowFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        models = Data.createModel(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show, container, false);

        list = view.findViewById(R.id.list);
        ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(),R.layout.item,models);
        list.setAdapter(listViewAdapter);
        return view;
    }


}