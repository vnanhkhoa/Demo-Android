package com.oumenreame.viewpager.data;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import com.oumenreame.viewpager.data.model.Item;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Data {
    public static ArrayList<Item> createModel(@NonNull Context context) {
        ArrayList<Item> items = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open("quiz.json");
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            StringBuilder builder = new StringBuilder();
            while (line != null) {
                builder.append(line);
                line=br.readLine();
            }
            String json=builder.toString();
            JSONArray jsonArray = new JSONArray(json);


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Item item = new Item();
                item.setTitle(object.getString("title"));
                item.setDetail(object.getString("detail"));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}
