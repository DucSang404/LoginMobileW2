package com.example.login.api;

import com.example.login.dto.AccountDTO;
import com.example.login.dto.MessageDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AcountAPI {
    @POST("/acount/login")
    Call<AccountDTO> login(@Body AccountDTO accountDTO);

    @POST("/acount/register")
    Call<MessageDTO> register (@Body String email);


    @POST("/acount/confirm-otp")
    Call<MessageDTO> confirmOTP (@Body String code);
}
