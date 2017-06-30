package com.task.vasskob.jobqueuetest.presenter;

import com.birbit.android.jobqueue.JobManager;
import com.task.vasskob.jobqueuetest.event.AvatarIsLoadEvent;
import com.task.vasskob.jobqueuetest.event.NameIsLoadEvent;
import com.task.vasskob.jobqueuetest.event.RepoCountIsLoadEvent;
import com.task.vasskob.jobqueuetest.job.UAvatarLoadJob;
import com.task.vasskob.jobqueuetest.job.UNameLoadJob;
import com.task.vasskob.jobqueuetest.job.URepoCountLoadJob;
import com.task.vasskob.jobqueuetest.view.MainView;

import de.greenrobot.event.EventBus;

public class MainPresenter implements IMainPresenter {

    private JobManager mJobManager;
    private MainView mView;

    public MainPresenter(JobManager mJobManager) {
        this.mJobManager = mJobManager;
    }

    @Override
    public void loadData() {
        initJobManager();
    }

    private void initJobManager() {
        mJobManager.addJobInBackground(new UAvatarLoadJob());
        mJobManager.addJobInBackground(new UNameLoadJob());
        mJobManager.addJobInBackground(new URepoCountLoadJob());
    }

    @Override
    public void attachView(MainView view) {
        this.mView = view;
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView() {
        mView = null;
        EventBus.getDefault().unregister(this);
    }

    @SuppressWarnings("unused")
    public void onEvent(AvatarIsLoadEvent event) {
        mView.showUserAvatar(event.getAvatarUrl());
    }

    @SuppressWarnings("unused")
    public void onEvent(NameIsLoadEvent event) {
        mView.showUserName(event.getUName());
    }

    @SuppressWarnings("unused")
    public void onEvent(RepoCountIsLoadEvent event) {
        mView.showUserRepoCount(event.getURepoCount());
    }
}
