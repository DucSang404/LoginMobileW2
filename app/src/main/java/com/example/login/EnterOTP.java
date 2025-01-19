package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.api.AccountAPI;
import com.example.login.api.RetrofitClient;
import com.example.login.constant.ActionConstant;
import com.example.login.dto.AccountDTO;
import com.example.login.dto.MessageDTO;
import com.example.login.dto.request.RegisterDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

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
                    Toast.makeText(EnterOTP.this, "Xác thực OTP thành công", Toast.LENGTH_SHORT).show();
                    // Bạn có thể chuyển sang màn hình khác nếu cần
                } else {
                    Toast.makeText(EnterOTP.this, "OTP không hợp lệ hoặc đã hết hạn", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EnterOTP.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register (String otp, AccountDTO accountDTO) {
        try {
            AccountAPI api = RetrofitClient.getRetrofitInstance().create(AccountAPI.class);

            Call<MessageDTO> call = api.register(
                    new RegisterDTO(accountDTO.getUsername(),accountDTO.getPassword(),otp)
            );

            // Thực hiện gọi API
            call.enqueue(new Callback<MessageDTO>() {
                @Override
                public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Lấy dữ liệu phản hồi là một chuỗi
                        MessageDTO messageDTO = response.body();
                        Toast.makeText(EnterOTP.this, messageDTO.getMessage(), Toast.LENGTH_SHORT).show();

                        // thành công
                        if (messageDTO.isResult()) {
                            Intent intent = new Intent(EnterOTP.this, MainActivity.class);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(EnterOTP.this, "Lỗi hệ thống ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MessageDTO> call, Throwable t) {
                    Toast.makeText(EnterOTP.this, "Lỗi hệ thống ", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(EnterOTP.this, "Vui lòng nhập OTP", Toast.LENGTH_SHORT).show();
        } else {
            if (getIntent().getStringExtra(ActionConstant.ACTION).equals(ActionConstant.FORGET_PASSWORD))
                verifyOtp(email, otp);
            else if (getIntent().getStringExtra(ActionConstant.ACTION).equals(ActionConstant.REGISTER)) {
                AccountDTO accountDTO = (AccountDTO) getIntent().getSerializableExtra("account");
                register(otp,accountDTO);
            }
        }
    }
}
