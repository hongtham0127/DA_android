package com.example.da_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class activity_Register extends AppCompatActivity {

    EditText email,password,xacnhan_mk;
    Button btn_sign_up;
    ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_account);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        xacnhan_mk = findViewById(R.id.xacnhan_mk);
        btn_sign_up = findViewById(R.id.btnCreate_Account);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(email.getText().length() !=0 && password.getText().length() !=0 &&xacnhan_mk.getText()==password.getText())
                {
                    Toast.makeText(activity_Register.this,"Đăng Ký Thành Công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity_Register.this,activity_login.class);
                    startActivities(intent);
                }
                else
                {
                    Toast.makeText(activity_Register.this,"Đăng Ký thất bại",Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(activity_Register.this,null);
                    //startActivities(intent);
                }

            }
        });
    }

    private void startActivities(Intent intent) {
    }
}
