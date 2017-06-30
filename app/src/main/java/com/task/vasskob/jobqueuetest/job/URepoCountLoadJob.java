package com.task.vasskob.jobqueuetest.job;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.task.vasskob.jobqueuetest.Constants;
import com.task.vasskob.jobqueuetest.event.RepoCountIsLoadEvent;
import com.task.vasskob.jobqueuetest.model.User;
import com.task.vasskob.jobqueuetest.utils.Parser;

import java.io.IOException;

import de.greenrobot.event.EventBus;

public class URepoCountLoadJob extends Job {

    private static final String TAG = UAvatarLoadJob.class.getSimpleName();

    public URepoCountLoadJob() {
        super(new Params(Constants.PRIORITY_LOW)
                .requireNetwork()
                .persist()
                .delayInMs(3000)
                .groupBy(Groups.DETAIL_CONTENT));
    }

    @Override
    public void onAdded() {
        Log.d(TAG, "UAvatarLoadJob onAdded: ");

    }

    @Override
    public void onRun() throws Throwable {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "UAvatarLoadJob OkHttpClient onFailure: ", e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                User user = Parser.getParsedUser(json);
                EventBus.getDefault().post(new RepoCountIsLoadEvent(user.getPublic_repos()));
            }
        });
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Log.d(TAG, "UAvatarLoadJob onCancel: ");
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        Log.d(TAG, "UAvatarLoadJob shouldReRunOnThrowable: ");
        return null;
    }
}
