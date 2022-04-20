package com.oumenreame.viewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.oumenreame.viewpager.model.Model;

public class MainActivity3 extends AppCompatActivity {

    private TextView mTvTitle,mTvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTvTitle = findViewById(R.id.tvTitle);
        mTvDetail = findViewById(R.id.tvDetail);

        Intent intent = getIntent();
        if (intent.hasExtra("Model")) {
            Model model = (Model) intent.getSerializableExtra("Model");
            mTvDetail.setText(model.getDetail());
            mTvTitle.setText(model.getTitle());
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mTvTitle.setText(savedInstanceState.getString("Title"));
        mTvDetail.setText(savedInstanceState.getString("Detail"));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title",mTvTitle.getText().toString());
        outState.putString("Detail",mTvDetail.getText().toString());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}