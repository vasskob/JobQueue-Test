package com.task.vasskob.jobqueuetest;

import android.app.Application;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;


public class MyApplication extends Application {

    private MyApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public JobManager getJobManager() {
        Configuration config = new Configuration.Builder(mApp)
                .minConsumerCount(1)
                .maxConsumerCount(3)
                .loadFactor(3)
                .consumerKeepAlive(120)
                .build();
        return new JobManager(config);
    }

}
