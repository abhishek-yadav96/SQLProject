package com.example.sqlproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    UserModal userModal;

    TextView txt_stu_name,txt_email_id,txt_password,txt_father_name,txt_pincode;
    Button btn_update,btn_logout,all_users_details,btn_delete;
    String email1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initView();
        email1=getIntent().getStringExtra("email1");
        getUserDetails();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardActivity.this,UpdateDetailsActivity.class);
                intent.putExtra("key_userModal",userModal);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,MainActivity.class));
            }
        });

        all_users_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAllUsers();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(DashboardActivity.this);
                builder.setTitle("Delete Profile");
                builder.setMessage("Are you sure you want to delete your profile?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SqlLiteDBHelper sqlLiteDBHelper=new SqlLiteDBHelper(DashboardActivity.this);
                        boolean check=sqlLiteDBHelper.deleteProfileHelper(userModal.getEmail());
                        if(check){
                            Toast.makeText(DashboardActivity.this, "Deleted Successfully...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DashboardActivity.this,MainActivity.class));
                        }else {
                            Toast.makeText(DashboardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.show();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
       getUserDetails();
    }

    private void callAllUsers() {
        SqlLiteDBHelper sqlLiteDBHelper=new SqlLiteDBHelper(getApplicationContext());
        ArrayList al=sqlLiteDBHelper.getAllUserDetails();
        Toast.makeText(this, ""+al, Toast.LENGTH_SHORT).show();
    }

    private void getUserDetails() {
        SqlLiteDBHelper sqlLiteDBHelper=new SqlLiteDBHelper(getApplicationContext());
        ArrayList<UserModal> arrayList=sqlLiteDBHelper.getLoggedInUserDetails(email1);
        userModal=arrayList.get(0);
        txt_stu_name.setText(userModal.getName());
        txt_email_id.setText(userModal.getEmail());
        txt_password.setText(userModal.getPassword());
        txt_father_name.setText(userModal.getFather());
        txt_pincode.setText(userModal.getPincode());

    }

    private void initView() {
        txt_stu_name=findViewById(R.id.txt_stu_name);
        txt_email_id=findViewById(R.id.txt_email_id);
        txt_password=findViewById(R.id.txt_password);
        txt_father_name=findViewById(R.id.txt_father_name);
        txt_pincode=findViewById(R.id.txt_pin_code);

        btn_delete=findViewById(R.id.btn_delete);
        btn_update=findViewById(R.id.btn_update);
        btn_logout=findViewById(R.id.btn_logout);
        all_users_details=findViewById(R.id.btn_all_user_details);
    }
}