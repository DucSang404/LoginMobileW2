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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    private void register (AccountDTO account) {
        AccountAPI api = RetrofitClient.getRetrofitInstance().create(AccountAPI.class);
        Call<MessageDTO> call = api.sendOtpForRegister(account.getUsername());


        call.enqueue(new Callback<MessageDTO>() {
            @Override
            public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                if (response.isSuccessful()) {

                    MessageDTO messageDTO = response.body();
                    Toast.makeText(Register.this, messageDTO.getMessage(), Toast.LENGTH_SHORT).show();

                    // thành công
                    if (messageDTO.isResult()) {
                        // chuyển tới enter otp
                        Intent intent = new Intent(Register.this, EnterOTP.class);
                        intent.putExtra("account", account);
                        intent.putExtra(ActionConstant.ACTION, ActionConstant.REGISTER);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(Register.this, "Không thể gửi OTP. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageDTO> call, Throwable t) {
                Toast.makeText(Register.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void btnRegisterClick(View view) {
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etRePassword = findViewById(R.id.etRePassword);

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String rePassword = etRePassword.getText().toString().trim();

        // Check input
        if (email.isEmpty()) {
            etEmail.setError("Please enter your email.");
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Please enter your email.");
            return;
        }

        if (!password.equals(rePassword)) {
            etRePassword.setError("Passwords do not match!");
            return;
        }

        register (new AccountDTO(email,password));
    }

}
