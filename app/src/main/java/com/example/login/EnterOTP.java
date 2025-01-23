package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.api.AccountAPI;
import com.example.login.api.RetrofitClient;
import com.example.login.constant.ActionConstant;
import com.example.login.dto.AccountDTO;
import com.example.login.dto.MessageDTO;
import com.example.login.dto.request.RegisterDTO;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnterOTP extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_otp);
    }

    private void verifyOtp(String email, String otp) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/account/verify-otp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AccountAPI apiService = retrofit.create(AccountAPI.class);

        Call<Void> call = apiService.verifyOtp(email, otp);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EnterOTP.this, "OTP authentication successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EnterOTP.this, ResetPass.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else {
                    Toast.makeText(EnterOTP.this, "OTP is invalid or expired", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EnterOTP.this, "Connection error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register (String otp, AccountDTO accountDTO) {
        try {
            AccountAPI api = RetrofitClient.getRetrofitInstance().create(AccountAPI.class);

            Call<MessageDTO> call = api.register(
                    new RegisterDTO(accountDTO.getUsername(),accountDTO.getPassword(),otp)
            );

            call.enqueue(new Callback<MessageDTO>() {
                @Override
                public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        MessageDTO messageDTO = response.body();
                        Toast.makeText(EnterOTP.this, messageDTO.getMessage(), Toast.LENGTH_SHORT).show();

                        if (messageDTO.isResult()) {
                            Intent intent = new Intent(EnterOTP.this, MainActivity.class);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(EnterOTP.this, "System error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MessageDTO> call, Throwable t) {
                    Toast.makeText(EnterOTP.this, "System error ", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void btnLoginClick(View view) {
        EditText editText = findViewById(R.id.etOtp);
        String email = getIntent().getStringExtra("email");

        String otp = editText.getText().toString().trim();
        if (otp.isEmpty()) {
            Toast.makeText(EnterOTP.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
        } else {
            String action = getIntent().getStringExtra(ActionConstant.ACTION);

            if (ActionConstant.FORGET_PASSWORD.equals(action)) {
                verifyOtp(email, otp);
            } else if (ActionConstant.REGISTER.equals(action)) {
                AccountDTO accountDTO = (AccountDTO) getIntent().getSerializableExtra("account");
                register(otp, accountDTO);
            } else {
                Toast.makeText(EnterOTP.this, "Invalid action!", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
