package com.oumenreame.viewpager.core;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.TypedValue;

import androidx.annotation.NonNull;

import com.oumenreame.viewpager.model.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Data {
    public static ArrayList<Model> createModel(@NonNull Context context) {
        ArrayList<Model> models = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("quiz.json");
            InputStreamReader isr=new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br=new BufferedReader(isr);
            String line=br.readLine();
            StringBuilder builder=new StringBuilder();
            while (line != null)
            {
                builder.append(line);
                line=br.readLine();
            }
            String json=builder.toString();
            JSONArray jsonArray = new JSONArray(json);


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Model model = new Model();
                model.setTitle(object.getString("title"));
                model.setDetail(object.getString("detail"));
                models.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return models;
    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
