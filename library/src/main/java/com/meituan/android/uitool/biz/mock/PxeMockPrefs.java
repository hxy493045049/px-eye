package com.meituan.android.uitool.biz.mock;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meituan.android.uitool.biz.mock.structure.PxeFixedLinkedList;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author shawn
 * Created with IntelliJ IDEA.
 * 2018/11/8 on 下午5:22
 */
public class PxeMockPrefs {
    private static Gson gson = new Gson();
    private static String KEY = "mock_mis_numbers";
    private static String NAME = "mock_prefs";

    public static List<String> get(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(NAME, MODE_PRIVATE);
        return gson.fromJson(prefs.getString(KEY, ""), new TypeToken<List<String>>() {
        }.getType());
    }

    public static void save(Context context, PxeFixedLinkedList<String> misNumber) {
        SharedPreferences preferences = context.getSharedPreferences("count", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY, gson.toJson(misNumber));
        editor.apply();
    }
}
