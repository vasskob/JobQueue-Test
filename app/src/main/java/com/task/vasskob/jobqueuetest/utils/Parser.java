package com.task.vasskob.jobqueuetest.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.task.vasskob.jobqueuetest.model.User;

public class Parser {

    private static final String TAG = Parser.class.getSimpleName();

    public static User getParsedUser(String jsonLine) {
        try {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(jsonLine, User.class);
        } catch (Exception ex) {
            Log.e(TAG, "getParsedUser: ", ex);
        }
        return null;
    }
}
