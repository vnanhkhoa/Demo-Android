package com.oumenreame.viewpager.core;

import android.content.Context;
import android.util.TypedValue;

import com.oumenreame.viewpager.R;
import com.oumenreame.viewpager.model.Model;

import java.util.ArrayList;

public class Data {
    public static ArrayList<Model> createModel(Context context) {
        ArrayList<Model> models = new ArrayList<>();
        String[] arr = context.getResources().getString(R.string.nameString).split("\\.");
        for (String s:arr) {
            s = s.trim();
            String[] sArr = s.split(" ");
            if (sArr.length <4) continue;

            Model model = new Model(
                    (sArr[0]+" "+sArr[1]+" "+sArr[2]).toUpperCase(),
                    s
            );
            models.add(model);
        }

        return models;
    }

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
