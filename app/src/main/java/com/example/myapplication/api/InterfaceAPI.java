package com.example.myapplication.api;


import com.example.myapplication.model.OrdersData;
import com.example.myapplication.model.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterfaceAPI {
    @FormUrlEncoded
    @POST("testlogin.php")
    Call<String> checkLogin(@Field("Authorization") String authToken);

    @FormUrlEncoded
    @POST("testsign.php")
    Call<String> registration(@Field("Registration") String regToken);
//    Call<String> checkLogin(@Field("username") String login, @Field("password") String password);

    @POST("info.php")
    Call<List<OrdersData>> getOrdersJson();

    @FormUrlEncoded
    @POST("info_test.php")
    Call<List<OrdersData>> getTakenOrdersJson(@Field("username") String usernameToken);

    @FormUrlEncoded
    @POST("user.php")
    Call<List<UserData>> getUserJson(@Field("username") String username);

    @FormUrlEncoded
    @POST("reservation.php")
    Call<String> reservationOrder(@Field("Reservation") String reservToken);

    @FormUrlEncoded
    @POST("give_order.php")
    Call<String> giveOrder(@Field("leaseToken") String leaseToken);
}
