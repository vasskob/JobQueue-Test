package com.task.vasskob.jobqueuetest.view;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.birbit.android.jobqueue.JobManager;
import com.task.vasskob.jobqueuetest.MyApplication;
import com.task.vasskob.jobqueuetest.R;
import com.task.vasskob.jobqueuetest.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.tv_header)
    TextView tvHeader;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_detail)
    TextView tvDetail;

    @BindView(R.id.pb_header)
    ProgressBar pbHeader;

    @BindView(R.id.pb_title)
    ProgressBar pbTitle;

    @BindView(R.id.pb_detail)
    ProgressBar pbDetail;

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d(TAG, "onCreate: ");

        JobManager jobManager = ((MyApplication) getApplication()).getJobManager();
        presenter = new MainPresenter(jobManager);
        presenter.attachView(this);
    }

    @Override
    public void showHeader(final String data) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvHeader.setText(data);
                pbHeader.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showTitle(final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTitle.setText(data);
                pbTitle.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showDetail(final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pbDetail.setVisibility(View.GONE);
                tvDetail.setText(data);
            }
        });
    }

    @OnClick(R.id.btn_load)
    public void onLoadClick() {
        Log.d(TAG, "onLoadClick: ");
        runProgressBar();
        presenter.loadData();
    }

    private void runProgressBar() {
        pbHeader.setVisibility(View.VISIBLE);
        pbTitle.setVisibility(View.VISIBLE);
        pbDetail.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_clean)
    public void onCleanClick() {
        tvHeader.setText("");
        tvTitle.setText("");
        tvDetail.setText("");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
