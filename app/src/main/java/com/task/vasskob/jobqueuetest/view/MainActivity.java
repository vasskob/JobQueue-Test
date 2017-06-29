package com.task.vasskob.jobqueuetest.view;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.birbit.android.jobqueue.JobManager;
import com.bumptech.glide.Glide;
import com.task.vasskob.jobqueuetest.MyApplication;
import com.task.vasskob.jobqueuetest.R;
import com.task.vasskob.jobqueuetest.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.iv_avatar)
    ImageView ivUAvatar;

    @BindView(R.id.tv_user_name)
    TextView tvUName;

    @BindView(R.id.tv_user_repo_count)
    TextView tvURepoCount;

    @BindView(R.id.pb_avatar)
    ProgressBar pbUAvatar;

    @BindView(R.id.pb_name)
    ProgressBar pbUName;

    @BindView(R.id.pb_repo_count)
    ProgressBar pbURepoCount;

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainPresenter presenter;
    private JobManager mJobManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d(TAG, "onCreate: ");

        mJobManager = ((MyApplication) getApplication()).getJobManager();

        presenter = new MainPresenter(mJobManager);
        presenter.attachView(this);
    }

    @Override
    public void showUserName(final String data) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String label = getString(R.string.user_name);
                tvUName.setText(String.format(label, data));
                pbUName.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showUserAvatar(final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(MainActivity.this)
                        .load(data)
                        .into(ivUAvatar);
                pbUAvatar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showUserRepoCount(final int data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String label = getString(R.string.user_repo_count);
                tvURepoCount.setText(String.format(label, data));
                pbURepoCount.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.btn_load)
    public void onLoadClick() {
        Log.d(TAG, "onLoadClick: ");
        runProgressBar();
        presenter.loadData();
        //  initJobManager();

    }

    private void runProgressBar() {
        pbUName.setVisibility(View.VISIBLE);
        pbURepoCount.setVisibility(View.VISIBLE);
        pbUAvatar.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_clean)
    public void onCleanClick() {
        tvUName.setText("");
        tvURepoCount.setText("");
        ivUAvatar.setImageResource(R.drawable.ic_user_avatar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

//    private MainPresenter.OnDataReadyListener listener = new MainPresenter.OnDataReadyListener() {
//        @Override
//        public void onUNameReady(String data) {
//            showUserName(data);
//        }
//
//        @Override
//        public void onUAvatarReady(String data) {
//            showUserAvatar(data);
//        }
//
//        @Override
//        public void onURepoCountReady(int data) {
//            showUserRepoCount(data);
//        }
//    };

//    private void initJobManager() {
//        Params paramsLow = new Params(Constants.PRIORITY_LOW).requireNetwork().persist().delayInMs(3000);
//        Params paramsMiddle = new Params(Constants.PRIORITY_MIDDLE).requireNetwork().persist().delayInMs(2000);
//        Params paramsHeight = new Params(Constants.PRIORITY_HEIGHT).requireNetwork().persist();
//
//        UAvatarLoadJob jobHeader = new UAvatarLoadJob(paramsHeight, listener);
//        URepoCountLoadJob jobTitle = new URepoCountLoadJob(paramsMiddle, listener);
//        UNameLoadJob jobDetail = new UNameLoadJob(paramsLow, listener);
//
//        mJobManager.addJobInBackground(jobHeader);
//        mJobManager.addJobInBackground(jobTitle);
//        mJobManager.addJobInBackground(jobDetail);
//    }
}
