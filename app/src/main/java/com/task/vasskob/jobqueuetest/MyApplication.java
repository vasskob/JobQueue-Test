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
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .build();
        return new JobManager(config);
    }
}
