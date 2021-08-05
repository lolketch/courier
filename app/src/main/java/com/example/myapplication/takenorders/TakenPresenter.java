package com.example.myapplication.takenorders;

import android.util.Log;

import com.example.myapplication.contract.MainContract;

public class TakenPresenter implements MainContract.TakenPresenter {
    private static final String TAG = "MainPresenter";

    //Компоненты MVP приложения
    private MainContract.TakenView mView;
    private MainContract.TakenRepository mRepository;
    private String username;

    //Обратите внимание на аргументы конструктора - мы передаем экземпляр View
    public TakenPresenter(MainContract.TakenView mView,String username) {
        this.username = username;
        this.mView = mView;
        this.mRepository = new TakenRepository();
        Log.d(TAG, "Constructor");
    }

    @Override
    public void activityOnCreate(){
        mRepository.getOrdersResponse(mView,username);
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
