package com.example.login.api;

import com.example.login.dto.AccountDTO;
import com.example.login.dto.MessageDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountAPI {
    @POST("/account/login")
    Call<AccountDTO> login(@Body AccountDTO accountDTO);

    @POST("/account/register")
    Call<MessageDTO> register (@Body String email);


    @POST("/account/confirm-otp")
    Call<MessageDTO> confirmOTP (@Body String code);

    @FormUrlEncoded
    @POST("/account/send-otp")
    Call<Void> sendOtp(@Field("email") String email);

    @POST("/account/verify-otp")
    Call<Void> verifyOtp(@Query("email") String email, @Query("otp") String otp);
}
