package com.example.myapplication.MainActivity;

import android.util.Log;

import com.example.myapplication.contract.MainContract;

public class MainPresenter implements MainContract.Presenter {
    private static final String TAG = "MainPresenter";

    //Компоненты MVP приложения
    private MainContract.View mView;
    private MainContract.Repository mRepository;
    private  String username;

    //Обратите внимание на аргументы конструктора - мы передаем экземпляр View
    public MainPresenter(MainContract.View mView,String username) {
        this.mView = mView;
        this.mRepository = new MainRepository();
        this.username = username;
        Log.d(TAG, "Constructor");
    }

    @Override
    public void activityOnCreate(){
        mRepository.getOrdersResponse(mView,username);
        mRepository.getUserData(mView,username);
    }



    @Override
    public void onDestroy() {
        /**
         * Если бы мы работали например с RxJava, в этом классе стоило бы отписываться от подписок
         * Кроме того, при работе с другими методами асинхронного андроида здесь мы боремся с утечкой контекста
         */

        Log.d(TAG, "onDestroy()");
    }
}
