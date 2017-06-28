package com.task.vasskob.jobqueuetest.presenter;

import com.task.vasskob.jobqueuetest.view.MainView;

public interface IMainPresenter {
    void loadData();
    void attachView(MainView view);
    void detachView();
}
