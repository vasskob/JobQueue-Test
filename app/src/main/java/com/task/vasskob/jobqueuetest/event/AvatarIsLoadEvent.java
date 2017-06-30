package com.task.vasskob.jobqueuetest.event;

public class AvatarIsLoadEvent {

    private final String AvatarUrl;

    public AvatarIsLoadEvent(String avatarUrl) {
        AvatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return AvatarUrl;
    }
}
