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
import com.task.vasskob.jobqueuetest.presenter.MainPresenter;

import java.io.IOException;


public class HeaderLoadJob extends Job {

    private static final String TAG = HeaderLoadJob.class.getSimpleName();
    private final MainPresenter.OnDataReadyListener listener;

    private OkHttpClient client;
    private Request request;

    public HeaderLoadJob(Params params, MainPresenter.OnDataReadyListener listener) {
        super(params);
        this.listener = listener;
    }

    @Override
    public void onAdded() {
        Log.d(TAG, "HeaderLoadJob onAdded: ");
        client = new OkHttpClient();
        request = new Request.Builder()
                .url(Constants.URL)
                .build();
    }

    @Override
    public void onRun() throws Throwable {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e(TAG, "HeaderLoadJob OkHttpClient onFailure: ", e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d(TAG, "HeaderLoadJob onResponse: " + response.toString());
                listener.onHeaderReady(response.toString());
            }
        });
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Log.d(TAG, "HeaderLoadJob onCancel: ");
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        Log.d(TAG, "HeaderLoadJob shouldReRunOnThrowable: ");
        return null;
    }
}
