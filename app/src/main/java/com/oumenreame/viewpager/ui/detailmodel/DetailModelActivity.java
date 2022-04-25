package com.oumenreame.viewpager.ui.detailmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.core.Constant;
import com.oumenreame.viewpager.model.Model;

import java.util.Objects;

public class DetailModelActivity extends AppCompatActivity {

    private TextView mTvTitle,mTvDetail;
    final String M_TITLE = "Title";
    final String M_DETAIL = "Detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_model);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mTvTitle = findViewById(R.id.tvTitle);
        mTvDetail = findViewById(R.id.tvDetail);

        Intent intent = getIntent();
        if (intent.hasExtra(Constant.MODEL)) {
            Model model = (Model) intent.getSerializableExtra(Constant.MODEL);
            mTvDetail.setText(model.getDetail());
            mTvTitle.setText(model.getTitle());
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mTvTitle.setText(savedInstanceState.getString(M_TITLE));
        mTvDetail.setText(savedInstanceState.getString(M_DETAIL));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title",mTvTitle.getText().toString());
        outState.putString("Detail",mTvDetail.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}