package com.example.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.api.AccountAPI;

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

        EditText editText = findViewById(R.id.etOtp);
        Button verifyButton = findViewById(R.id.btnConfirm);
        String email = getIntent().getStringExtra("email");

        verifyButton.setOnClickListener(v -> {
            String otp = editText.getText().toString().trim();
            if (otp.isEmpty()) {
                Toast.makeText(EnterOTP.this, "Vui lòng nhập OTP", Toast.LENGTH_SHORT).show();
            } else {
                verifyOtp(email, otp);
            }
        });
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
}
