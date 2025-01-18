package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.api.AccountAPI;
import com.example.login.api.RetrofitClient;
import com.example.login.dto.AccountDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvRegisterHere = findViewById(R.id.tvRegisterHere);
        TextView tvForgetPassword = findViewById(R.id.tvForgetPassword);

        tvRegisterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến ForgotPasswordActivity
                Intent intent = new Intent(MainActivity.this, ForgetPassword.class);
                startActivity(intent);
            }
        });
    }

    private void login (AccountDTO accountDTO) {
        AccountAPI api = RetrofitClient.getRetrofitInstance().create(AccountAPI.class);
        Call<AccountDTO> call = api.login(accountDTO);

        // Thực hiện gọi API
        call.enqueue(new Callback<AccountDTO>() {
            @Override
            public void onResponse(Call<AccountDTO> call, Response<AccountDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountDTO loggedInAccount = response.body();
                    Intent intent = new Intent(MainActivity.this, LoginSuccess.class);
                    intent.putExtra("username", loggedInAccount.getUsername());
                    startActivity(intent);
//                    Toast.makeText(MainActivity.this, "Đăng nhập thành công với username: " + loggedInAccount.getUsername(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountDTO> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi đăng nhập: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnLoginClick(View view) {
        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Check input
        if (username.isEmpty()) {
            etUsername.setError("Please enter your email.");
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Please enter your password.");
            return;
        }

        login(new AccountDTO(username, password));
    }
}
