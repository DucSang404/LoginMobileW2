package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.api.AccountAPI;
import com.example.login.constant.ActionConstant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetPassword extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        EditText editText = findViewById(R.id.etEmail);
        Button sendOTP = findViewById(R.id.btnSendOtp);
        sendOTP.setOnClickListener(v -> {
            String email = editText.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(ForgetPassword.this, "Please enter email", Toast.LENGTH_SHORT).show();
                return;
            }

            sendOtpToBackend(email);
        });
    }

    private void sendOtpToBackend(String email) {
        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/account/send-otp/") // Thay đổi URL của backend
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AccountAPI apiService = retrofit.create(AccountAPI.class);

        Call<Void> call = apiService.sendOtp(email);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ForgetPassword.this, "OTP has sent to email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgetPassword.this, EnterOTP.class);
                    intent.putExtra("email", email);
                    intent.putExtra(ActionConstant.ACTION, ActionConstant.FORGET_PASSWORD);
                    startActivity(intent);
                } else {
                    Toast.makeText(ForgetPassword.this, "Unable to send OTP. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ForgetPassword.this, "Connection error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}