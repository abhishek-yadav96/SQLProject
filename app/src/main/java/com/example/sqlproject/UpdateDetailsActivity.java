package com.example.sqlproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateDetailsActivity extends AppCompatActivity {

    EditText up_student_name,up_pass,up_con_pass,up_father_name,up_pincode;
    TextView txt_email_id;
    Button up_update;
    UserModal userModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);
//   Initialize variable and object-----------------------------------------------------------------
        initView();
        userModal=(UserModal) getIntent().getSerializableExtra("key_userModal");

        up_student_name.setText(userModal.getName());
        txt_email_id.setText(userModal.getEmail());
        up_pass.setText(userModal.getPassword());
        up_father_name.setText(userModal.getFather());
        up_pincode.setText(userModal.getPincode());


        up_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=up_student_name.getText().toString();
                String pass=up_pass.getText().toString();
                String father=up_father_name.getText().toString();
                String pincode=up_pincode.getText().toString();


                SqlLiteDBHelper sqlLiteDBHelper=new SqlLiteDBHelper(UpdateDetailsActivity.this);
                boolean check=sqlLiteDBHelper.updateProfileHelper(name,userModal.getEmail(),pass,father,pincode);
                if(check){
                    Toast.makeText(UpdateDetailsActivity.this, "Values Updated Successfully", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else
                {
                    Toast.makeText(UpdateDetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initView() {

        up_student_name=findViewById(R.id.up_stu_name);
        txt_email_id=findViewById(R.id.txt_email_id);
        up_pass=findViewById(R.id.up_pass);
        up_father_name=findViewById(R.id.up_father_name);
        up_pincode=findViewById(R.id.up_pin_code);

        up_update=findViewById(R.id.up_update);


    }
}