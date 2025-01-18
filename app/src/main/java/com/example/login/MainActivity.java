package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "http://10.0.2.2:8080/login";

        Button btnLogin = findViewById(R.id.btnLogin);
        EditText edtUsername = findViewById(R.id.etUsername);
        EditText edtPassword = findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(view -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Username và Password không được để trống", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject loginRequest = new JSONObject();
                loginRequest.put("username", username);
                loginRequest.put("password", password);

                Log.d("LoginRequest", "Sending login request: " + loginRequest.toString());

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, loginRequest,
                        response -> {
                            try {
                                boolean loginSuccess = response.getBoolean("body");
                                Log.d("LoginResponse", "Login success: " + loginSuccess);

                                if (loginSuccess) {
                                    Intent intent = new Intent(MainActivity.this, LoginSuccess.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Invalid username or password!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            // Log chi tiết lỗi HTTP
                            Log.e("LoginError", "Error occurred: " + error.toString());
                            if (error.networkResponse != null) {
                                Log.e("LoginError", "HTTP Status Code: " + error.networkResponse.statusCode);
                            }
                            Toast.makeText(getApplicationContext(), "Error occurred during login!", Toast.LENGTH_SHORT).show();
                        });
                Volley.newRequestQueue(this).add(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

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
}