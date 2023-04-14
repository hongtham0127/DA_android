package com.example.da_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class activity_SignUp extends AppCompatActivity {
    EditText email,password,xacnhan_mk;
    Button btn_sign_up;
    ProgressBar progressBar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = this;

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
                    Toast.makeText(context,"Đăng Ký Thành Công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,activity_login.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(context,"Đăng Ký thất bại",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,null);
                    startActivity(intent);
                }

            }
        });
    }
}