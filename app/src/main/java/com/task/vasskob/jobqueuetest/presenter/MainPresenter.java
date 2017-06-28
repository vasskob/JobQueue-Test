package com.task.vasskob.jobqueuetest.presenter;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.Params;
import com.task.vasskob.jobqueuetest.Constants;
import com.task.vasskob.jobqueuetest.job.DetailLoadJob;
import com.task.vasskob.jobqueuetest.job.HeaderLoadJob;
import com.task.vasskob.jobqueuetest.job.TitleLoadJob;
import com.task.vasskob.jobqueuetest.view.MainView;

public class MainPresenter implements IMainPresenter{

    private static final String TAG = MainPresenter.class.getSimpleName();
    private JobManager mJobManager;
    private MainView mView;
    private OnDataReadyListener listener = new OnDataReadyListener() {
        @Override
        public void onHeaderReady(String data) {
            mView.showHeader(data);
        }

        @Override
        public void onTitleReady(String data) {
            mView.showTitle(data);
        }

        @Override
        public void onDetailReady(String data) {
            mView.showDetail(data);
        }
    };

    public MainPresenter(JobManager mJobManager) {
        this.mJobManager = mJobManager;
        mJobManager.start();
    }

    @Override
    public void loadData() {
        initJobManager();
    }

    private void initJobManager() {
        Params paramsLow = new Params(Constants.PRIORITY_LOW).requireNetwork().delayInMs(3000);
        Params paramsMiddle = new Params(Constants.PRIORITY_MIDDLE).requireNetwork().delayInMs(2000);
        Params paramsHeight = new Params(Constants.PRIORITY_HEIGHT).requireNetwork();

        HeaderLoadJob jobHeader = new HeaderLoadJob(paramsHeight, listener);
        TitleLoadJob jobTitle = new TitleLoadJob(paramsMiddle, listener);
        DetailLoadJob jobDetail = new DetailLoadJob(paramsLow, listener);

        mJobManager.addJobInBackground(jobHeader);
        mJobManager.addJobInBackground(jobTitle);
        mJobManager.addJobInBackground(jobDetail);
    }

    @Override
    public void attachView(MainView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public interface OnDataReadyListener {
        void onHeaderReady(String data);
        void onTitleReady(String data);
        void onDetailReady(String data);
    }
}
