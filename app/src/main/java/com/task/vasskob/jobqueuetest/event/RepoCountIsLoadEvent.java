package com.task.vasskob.jobqueuetest.event;

public class RepoCountIsLoadEvent {

    private final int uRepoCount;

    public RepoCountIsLoadEvent(int count) {
        uRepoCount = count;
    }

    public int getURepoCount() {
        return uRepoCount;
    }
}
