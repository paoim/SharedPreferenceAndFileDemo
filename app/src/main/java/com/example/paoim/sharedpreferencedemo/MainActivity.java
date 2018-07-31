package com.example.paoim.sharedpreferencedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome, tvResult;
    private EditText etFirstName, etLastName, etMobilePhone, etEmail;
    private Button btnSave;

    private final static String FILE_NAME = "myContact.txt";

    private boolean isValidateInput(String inputFirstName, String inputLastName, String inputMobilePhone, String inputEmail) {
        if (0 == inputFirstName.length()) {
            Toast.makeText(MainActivity.this, "Please input your first name!", Toast.LENGTH_LONG).show();
            etFirstName.setFocusableInTouchMode(true);
            return false;
        }
        Log.d("inputFirstName", inputFirstName);

        if (0 == inputLastName.length()) {
            Toast.makeText(MainActivity.this, "Please input your last name!", Toast.LENGTH_LONG).show();
            etLastName.setFocusableInTouchMode(true);
            return false;
        }
        Log.d("inputLastName", inputLastName);

        if (0 == inputMobilePhone.length() || 0 == inputEmail.length()) {
            if (0 == inputMobilePhone.length()) {
                Toast.makeText(MainActivity.this, "Please input your mobile phone!", Toast.LENGTH_LONG).show();
                etMobilePhone.setFocusableInTouchMode(true);
            }
            if (0 == inputEmail.length()) {
                Toast.makeText(MainActivity.this, "Please input your email!", Toast.LENGTH_LONG).show();
                etEmail.setFocusableInTouchMode(true);
            }
            return false;
        }
        Log.d("inputMobilePhone", inputMobilePhone);
        Log.d("inputEmail", inputEmail);

        return true;
    }

    private void onClick(View v) {
        String inputFirstName = etFirstName.getText().toString().trim();
        String inputLastName = etLastName.getText().toString().trim();
        String inputMobilePhone = etMobilePhone.getText().toString().trim();
        String inputEmail = etEmail.getText().toString().trim();

        if (isValidateInput(inputFirstName, inputLastName, inputMobilePhone, inputEmail)) {
            String result = inputFirstName + " " + inputLastName;
            if (0 < inputMobilePhone.length()) {
                result = result + ", " + inputMobilePhone;
            }
            if (0 < inputEmail.length()) {
                result = result + ", " + inputEmail;
            }

            if (0 < result.length()) {
                result = result.trim();
            }
            Log.d("result", result);

            if (0 == result.length()) {
                Toast.makeText(MainActivity.this, "Please input!", Toast.LENGTH_LONG).show();
            } else {
                saveDataToStoreInFile(result);
                tvResult.setText(result);
                finish();//close current Intent
            }
        }
    }

    private void loadDataFromFile() {
        File file = getApplicationContext().getFileStreamPath(FILE_NAME);
        if (file.exists()) {
            try {
                String line;
                String result = "";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(FILE_NAME)));
                while (null != (line = bufferedReader.readLine())) {
                    result = result + line;
                }
                tvResult.setText(result);
                bufferedReader.close();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void saveDataToStoreInFile(String data) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(data + "\n");
            outputStreamWriter.flush();
            outputStreamWriter.close();
            Toast.makeText(MainActivity.this, "Successfully saved!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvResult = (TextView) findViewById(R.id.tvResult);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etMobilePhone = (EditText) findViewById(R.id.etMobilePhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnSave = (Button) findViewById(R.id.btnSave);

        loadDataFromFile();
        btnSave.setOnClickListener(this::onClick);
    }
}
