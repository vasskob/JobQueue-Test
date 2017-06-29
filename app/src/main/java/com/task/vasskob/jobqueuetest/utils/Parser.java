package com.task.vasskob.jobqueuetest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.task.vasskob.jobqueuetest.model.User;

public class Parser {

    public static User getParsedUser(String jsonLine) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonLine, User.class);
    }
}
