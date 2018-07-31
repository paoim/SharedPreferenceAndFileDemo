package com.example.paoim.sharedpreferencedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private EditText etUsername, etPassword, etPasswordConfirmation;
    private Button btnSubmit;

    private final int CHILD_ACTIVITY = 1;
    private final static String SHARED_PREF_FILENAME = "com.example.paoim.sharedpreferencedemo.myunandpwd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPasswordConfirmation = (EditText) findViewById(R.id.etPasswordConfirmation);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_FILENAME, MODE_PRIVATE); // Mode Private - means can use only this application
        String userName = sharedPreferences.getString("userName", "");

        if (0 < userName.length()) {
            tvWelcome.setText("This app is logged by " + userName);
            etUsername.setText(userName);
            etUsername.setFocusable(false);
        } else {
            etUsername.setFocusableInTouchMode(true);
        }

        btnSubmit.setOnClickListener(this::onClick);
    }

    /**
     * It is method reference
     * @param v
     */
    private void onClick(View v) {
        String inputUser = etUsername.getText().toString().trim();
        tvWelcome.setText("This app is logged by " + inputUser);

        if (isValidateInput(inputUser)) {
            SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREF_FILENAME, MODE_PRIVATE).edit();
            editor.putString("user", inputUser);
            editor.commit();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivityForResult(intent, CHILD_ACTIVITY);
        }
    }

    private boolean isValidateInput(String inputUser) {
        String inputPassword = etPassword.getText().toString().trim();
        String inputConfirm = etPasswordConfirmation.getText().toString().trim();

        if (0 == inputUser.length()) {
            Toast.makeText(LoginActivity.this, "Please input your user name!", Toast.LENGTH_LONG).show();
            etUsername.setFocusableInTouchMode(true);
            return false;
        }

        if (0 == inputPassword.length()) {
            Toast.makeText(LoginActivity.this, "Please input your password!", Toast.LENGTH_LONG).show();
            etPassword.setFocusableInTouchMode(true);
            return false;
        }

        if (0 == inputConfirm.length()) {
            Toast.makeText(LoginActivity.this, "Please input your password confirmation!", Toast.LENGTH_LONG).show();
            etPasswordConfirmation.setFocusableInTouchMode(true);
            return false;
        }

        if (!inputPassword.equals(inputConfirm)) {
            etPasswordConfirmation.setText("");
            Toast.makeText(LoginActivity.this, "Your password and confirmation not match!", Toast.LENGTH_LONG).show();
            etPasswordConfirmation.setFocusableInTouchMode(true);
            return false;
        }

        return true;
    }
}
