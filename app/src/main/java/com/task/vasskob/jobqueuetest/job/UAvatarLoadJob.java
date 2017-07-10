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
import com.task.vasskob.jobqueuetest.event.AvatarIsLoadEvent;
import com.task.vasskob.jobqueuetest.model.User;
import com.task.vasskob.jobqueuetest.utils.Parser;

import java.io.IOException;

import de.greenrobot.event.EventBus;

public class UAvatarLoadJob extends Job {

    private static final String TAG = UAvatarLoadJob.class.getSimpleName();

    public UAvatarLoadJob() {
        super(new Params(Constants.PRIORITY_HEIGHT)
                .requireNetwork()
                .persist()
                .groupBy(Groups.MAIN_CONTENT));
    }

    @Override
    public void onAdded() {
        Log.d(TAG, "UAvatarLoadJob onAdded: ");
    }

    @Override
    public void onRun() throws Throwable {
        Request request = new Request.Builder()
                .url(Constants.URL)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "UAvatarLoadJob OkHttpClient onFailure: ", e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                User user = Parser.getParsedUser(json);

                assert user != null;
                EventBus.getDefault().post(new AvatarIsLoadEvent(user.getAvatar_url()));
                Log.d(TAG, "UAvatarLoadJob onResponse: !!! " + json);
            }
        });
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Log.d(TAG, "UAvatarLoadJob onCancel: ");
        // Job has exceeded retry attempts or shouldReRunOnThrowable() has decided to cancel.
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        Log.d(TAG, "UAvatarLoadJob shouldReRunOnThrowable: ");
        // An error occurred in onRun.
        // Return value determines whether this job should retry or cancel. You can further
        // specify a backoff strategy or change the job's priority. You can also apply the
        // delay to the whole group to preserve jobs' running order.
        return RetryConstraint.createExponentialBackoff(runCount, 1000);
    }
}
