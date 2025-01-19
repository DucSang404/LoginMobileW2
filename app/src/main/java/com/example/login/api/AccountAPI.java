package com.example.login.api;

import com.example.login.dto.AccountDTO;
import com.example.login.dto.MessageDTO;
import com.example.login.dto.request.RegisterDTO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountAPI {
    @POST("/account/login")
    Call<AccountDTO> login(@Body AccountDTO accountDTO);

    @POST("/account/register")
    Call<MessageDTO> register(@Body RegisterDTO registerDTO);

    @POST("/account/check-user")
    Call<MessageDTO> checkUser(@Body String email);

    @FormUrlEncoded
    @POST("/account/send-otp-for-register")
    Call<MessageDTO> sendOtpForRegister(@Field("email") String email);

    @FormUrlEncoded
    @POST("/account/send-otp")
    Call<Void> sendOtp(@Field("email") String email);

    @POST("/account/verify-otp")
    Call<Void> verifyOtp(@Query("email") String email, @Query("otp") String otp);

    @FormUrlEncoded
    @POST("/account/reset-pass")
    Call<Void> resetPass(@Field("email") String email, @Field("password") String password);

}
