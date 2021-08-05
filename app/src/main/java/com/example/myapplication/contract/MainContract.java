package com.example.myapplication.contract;

import android.content.Context;

import com.example.myapplication.model.OrdersData;
import com.example.myapplication.model.UserData;

import java.util.List;

public interface MainContract {
    interface View {
        void onSuccess(List<OrdersData> data,String username);
        List<UserData> onSuccessUser(List<UserData> data);
    }

    interface Presenter {
        void onDestroy();
        void activityOnCreate();
    }

    interface Repository {
        void getOrdersResponse(MainContract.View mView,String username);
        void getUserData(MainContract.View mView,String username);
    }

    interface TakenView {
        void onSuccess(List<OrdersData> data);
    }

    interface TakenPresenter {
        void onDestroy();
        void activityOnCreate();
    }

    interface TakenRepository {
        void getOrdersResponse(MainContract.TakenView mView,String usernameToken);
    }

    interface LoginView {
        void onSuccess(List<OrdersData> data);
    }

    interface LoginPresenter {
        void btn_pressed(String username,String password, Context context);
        void onDestroy();
        void activityOnCreate();
    }

    interface LoginRepository {
        String createAuthToken(String login, String password);
        void checkLoginDetails(String authToken,MainContract.LoginView mView,Context context,String username);
    }

    interface SignUpView {
    }

    interface SignUpPresenter {
        void btn_pressed(String first_name,String sec_name,String username,String password,String email, Context context);
        void onDestroy();
        void activityOnCreate();
    }

    interface SignUpRepository {
        String createAuthToken(String first_name, String sec_name,String username, String password, String email);
        void registrationDetails(String regToken, MainContract.SignUpView mView, Context context);
    }

}
