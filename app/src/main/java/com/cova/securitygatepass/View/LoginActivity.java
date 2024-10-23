package com.cova.securitygatepass.View;

import static com.cova.securitygatepass.Config.Constant.SHRD_PREF;
import static com.cova.securitygatepass.Config.Constant.SHRD_PREF_USER_NAME;
import static com.cova.securitygatepass.Config.Constant.SHRD_PREF_USER_ROLE;
import static com.cova.securitygatepass.Config.Constant.SHRD_PREF_USER_TOKEN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.cova.securitygatepass.Model.LoginModel.LoginRequest;
import com.cova.securitygatepass.R;
import com.cova.securitygatepass.ViewModel.AppViewModel;
import com.auth0.android.jwt.JWT;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private Context context;
    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin;
    private AppViewModel appViewModel;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;

    private Boolean isFirstTry = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        initControls();

        appViewModel.getTokenLiveData().observe(this, loginModel -> {
            progressBar.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
            if (loginModel != null) {
                if (loginModel.isIsSuccess()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SHRD_PREF_USER_TOKEN, "Bearer " + loginModel.getResult().getToken());
                    editor.putString(SHRD_PREF_USER_NAME, loginModel.getResult().getUser().getUUserName());
                    String token = loginModel.getResult().getToken();
                    JWT jwt = new JWT(token);
                    String role = jwt.getClaim("role").asString();
                    editor.putString(SHRD_PREF_USER_ROLE, role);
                    editor.apply();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(context, loginModel.getMessage().getDescription(), Toast.LENGTH_SHORT).show();
                }
            } else {
                if (isFirstTry) {
                    LoginRequest body = new LoginRequest();
                    body.setUUserName(etUsername.getText().toString().trim());
                    body.setPassword(etPassword.getText().toString().trim());
                    appViewModel.getToken(body);
                    isFirstTry = false;
                } else {
                    Toast.makeText(context, "Something went wrong.!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initControls() {
        context = this;
        etUsername = findViewById(R.id.activity_login_et_username);
        etPassword = findViewById(R.id.activity_login_et_password);
        btnLogin = findViewById(R.id.activity_login_btn_login);
        progressBar = findViewById(R.id.activity_login_progressbar);

        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        sharedPreferences = getSharedPreferences(SHRD_PREF, Context.MODE_PRIVATE);
        btnLogin.setOnClickListener(view -> {
            isFirstTry = true;
            if (etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                Toast.makeText(context, "Please enter valid Username and Password", Toast.LENGTH_SHORT).show();
            } else {
                btnLogin.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                LoginRequest body = new LoginRequest();
                body.setUUserName(etUsername.getText().toString().trim());
                body.setPassword(etPassword.getText().toString().trim());
                appViewModel.getToken(body);
            }
        });
        //etUsername.setText("manager2");
        //etPassword.setText("manager2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        isFirstTry = true;
    }
}