package com.example.sqlproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
//    Declare variable and reference----------------------------------------------------------------
    EditText email_id,password;
    TextView register;
    Button login;
    SqlLiteDBHelper sqlLiteDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//  Initialize variable and reference--------------------------------------------------------------
        initView();

//  Click Login button (Work perform)---------------------------------------------------------------
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//       call login With Email or Password----------------------------------------------------------\
            String email=email_id.getText().toString().trim();
            String pass=password.getText().toString().trim();
            if(email.isEmpty()){
                email_id.setError("Please enter email id");
            }else if(pass.isEmpty()){
                password.setError("Please enter your password");
            }else {
                callLogin(email,pass);
            }
        }
    });

//  Click register button move register page--------------------------------------------------------
    register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,RegisterActivity.class));
        }
    });
    }

//  Definition of callLogin() method (use in Login button)------------------------------------------
    private void callLogin(String email, String pass) {

        boolean loggedin=sqlLiteDBHelper.login(email,pass);
        if(loggedin){
            Intent intent=new Intent(MainActivity.this,DashboardActivity.class);
            intent.putExtra("email1",email);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Email ID and password Didn't matched", Toast.LENGTH_SHORT).show();
        }

    }

    //  Here define initView() method (Initialize variable and find View ID)----------------------------
    private void initView() {
        sqlLiteDBHelper=new SqlLiteDBHelper(getApplicationContext());
        email_id=findViewById(R.id.email_id);
        password=findViewById(R.id.password);
        register=findViewById(R.id.txtbtn_register);
        login=findViewById(R.id.btn_login);

    }
}