package com.example.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.api.AccountAPI;
import com.example.login.api.RetrofitClient;
import com.example.login.dto.MessageDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    private void register(String email) {
        AccountAPI api = RetrofitClient.getRetrofitInstance().create(AccountAPI.class);
        Call<MessageDTO> call = api.register(email);

        // Thực hiện gọi API
        call.enqueue(new Callback<MessageDTO>() {
            @Override
            public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy dữ liệu phản hồi là một chuỗi
                    MessageDTO messageDTO = response.body();
                    Toast.makeText(Register.this, messageDTO.getMessage() , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, "Chưa thể tạo OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageDTO> call, Throwable t) {
                Toast.makeText(Register.this, "Chưa thể tạo OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmOtp (String otp) {
        AccountAPI api = RetrofitClient.getRetrofitInstance().create(AccountAPI.class);
        Call<MessageDTO> call = api.confirmOTP(otp);

        // Thực hiện gọi API
        call.enqueue(new Callback<MessageDTO>() {
            @Override
            public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy dữ liệu phản hồi là một chuỗi
                    MessageDTO messageDTO = response.body();
                    Toast.makeText(Register.this, messageDTO.getMessage() , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, "Chưa thể xác nhận OTP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageDTO> call, Throwable t) {
                Toast.makeText(Register.this, "Chưa thể xác nhận OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void btnRegisterClick(View view) {
        EditText etEmail = findViewById(R.id.etEmail);

        String email = etEmail.getText().toString().trim();

        // Check input
        if (email.isEmpty()) {
            etEmail.setError("Please enter your email.");
            return;
        }

        register(email);
    }

    public void btnConfirmOtpClick(View view) {

        EditText etOtp = findViewById(R.id.etOtp);

        String otp = etOtp.getText().toString().trim();

        // Check input
        if (otp.isEmpty()) {
            etOtp.setError("Please enter your email.");
            return;
        }

        confirmOtp(otp);
    }
}
