package com.task.vasskob.jobqueuetest.event;

public class NameIsLoadEvent {

    private final String uName;

    public NameIsLoadEvent(String name) {
        uName = name;
    }

    public String getUName() {
        return uName;
    }
}
