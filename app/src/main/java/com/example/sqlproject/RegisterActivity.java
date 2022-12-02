package com.example.sqlproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
//Declare variable and reference--------------------------------------------------------------------
    EditText edt_student_name,edt_email_id,edt_pass,edt_con_pass,edt_father_name,edt_pincode;
    Button btn_submit;
    SqlLiteDBHelper sqlLiteDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//  Initialize variable and reference and find View id----------------------------------------------
        initView();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stu_name,email_id,pass,con_pass,father_name,pincode;
                stu_name=edt_student_name.getText().toString().trim();
                email_id=edt_email_id.getText().toString().trim();
                pass=edt_pass.getText().toString().trim();
                con_pass=edt_con_pass.getText().toString().trim();
                father_name=edt_father_name.getText().toString().trim();
                pincode=edt_pincode.getText().toString();
//
//                if(stu_name.isEmpty()){
//                    edt_student_name.setError("Please Student name");
//                }else if(email_id.isEmpty()){
//                    edt_email_id.setError("Enter email Id");
//                }else if(pass.isEmpty() && con_pass.isEmpty()){
//                    edt_con_pass.setError("Enter confirm password");
//                    edt_pass.setError("Enter Password");
//                }else if(father_name.isEmpty()){
//                    edt_father_name.setError("Enter father name");
//                }else if(pincode.isEmpty()){
//                    edt_pincode.setError("Enter Pin Code");
//                }else {
                    callRegisterData(stu_name,email_id,con_pass,father_name,pincode);
//                }

            }
        });

    }

    private void callRegisterData(String stu_name, String email_id, String con_pass, String father_name, String pincode) {

        boolean b=sqlLiteDBHelper.registerUserDetails(stu_name,email_id,con_pass,father_name,pincode);
        if(b){
            Toast.makeText(this, "Data inserted Successfully...", Toast.LENGTH_SHORT).show();
            openAlertDialog();

        }else {

            Toast.makeText(this, "User Register Failed!", Toast.LENGTH_SHORT).show();
        }

    }

    private void openAlertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage("Data inserted Successfully...");
        builder.setTitle("Data Insert");
        builder.setCancelable(false);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RegisterActivity.this, "Thank you", Toast.LENGTH_SHORT).show();
                edt_student_name.setText("");
                edt_email_id.setText("");
                edt_pass.setText("");
                edt_con_pass.setText("");
                edt_father_name.setText("");
                edt_pincode.setText("");

            }
        });
        builder.create();
        builder.show();
    }


    private void initView() {

        sqlLiteDBHelper=new SqlLiteDBHelper(getApplicationContext());

        edt_student_name=findViewById(R.id.edt_stu_name);
        edt_email_id=findViewById(R.id.edt_email_id);
        edt_pass=findViewById(R.id.edt_pass);
        edt_con_pass=findViewById(R.id.edt_con_pass);
        edt_father_name=findViewById(R.id.edt_father_name);
        edt_pincode=findViewById(R.id.edt_pin_code);

        btn_submit=findViewById(R.id.btn_submit);
    }
}