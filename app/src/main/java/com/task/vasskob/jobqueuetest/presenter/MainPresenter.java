package com.task.vasskob.jobqueuetest.presenter;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.Params;
import com.task.vasskob.jobqueuetest.Constants;
import com.task.vasskob.jobqueuetest.job.UAvatarLoadJob;
import com.task.vasskob.jobqueuetest.job.UNameLoadJob;
import com.task.vasskob.jobqueuetest.job.URepoCountLoadJob;
import com.task.vasskob.jobqueuetest.view.MainView;

import java.io.Serializable;

public class MainPresenter implements IMainPresenter, Serializable {

    private static final String TAG = MainPresenter.class.getSimpleName();
    private JobManager mJobManager;
    private MainView mView;
    private OnDataReadyListener listener = new OnDataReadyListener() {
        @Override
        public void onUNameReady(String data) {
            mView.showUserName(data);
        }

        @Override
        public void onUAvatarReady(String data) {
            mView.showUserAvatar(data);
        }

        @Override
        public void onURepoCountReady(int data) {
            mView.showUserRepoCount(data);
        }
    };

    public MainPresenter(JobManager mJobManager) {
        this.mJobManager = mJobManager;
        //  mJobManager.start();
    }

    @Override
    public void loadData() {
        initJobManager();
    }

    private void initJobManager() {
        Params paramsLow = new Params(Constants.PRIORITY_LOW).requireNetwork().delayInMs(3000);
        Params paramsMiddle = new Params(Constants.PRIORITY_MIDDLE).requireNetwork().delayInMs(2000);
        Params paramsHeight = new Params(Constants.PRIORITY_HEIGHT).requireNetwork();

        UAvatarLoadJob jobAvatar = new UAvatarLoadJob(paramsHeight, listener);
        UNameLoadJob jobName = new UNameLoadJob(paramsMiddle, listener);
        URepoCountLoadJob jobRepos = new URepoCountLoadJob(paramsLow, listener);

        mJobManager.addJobInBackground(jobAvatar);
        mJobManager.addJobInBackground(jobName);
        mJobManager.addJobInBackground(jobRepos);
    }

    @Override
    public void attachView(MainView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public interface OnDataReadyListener extends Serializable {
        void onUNameReady(String data);

        void onUAvatarReady(String data);

        void onURepoCountReady(int data);
    }
}
