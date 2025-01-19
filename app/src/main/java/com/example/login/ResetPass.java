package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.login.api.AccountAPI;
import com.example.login.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResetPass extends AppCompatActivity {
    private EditText etPassword, etRePassword;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pass);

        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass1 = etPassword.getText().toString().trim();
                String pass2 = etRePassword.getText().toString().trim();
                String email = getIntent().getStringExtra("email");

                if (TextUtils.isEmpty(pass1) || TextUtils.isEmpty(pass2)) {
                    Toast.makeText(ResetPass.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass1.equals(pass2)) {
                    Toast.makeText(ResetPass.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                } else {
                    sendPasswordUpdate(email, pass1);
                }
            }
        });
    }

    private void sendPasswordUpdate(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AccountAPI apiService = retrofit.create(AccountAPI.class);

        Call<Void> call = apiService.resetPass(email, password);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ResetPass.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ResetPass.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ResetPass.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ResetPass.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
