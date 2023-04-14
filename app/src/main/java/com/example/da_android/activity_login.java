package com.example.da_android;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class activity_login extends AppCompatActivity {
    private void startActivities(Intent intent) {
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        Button btn_login = (Button) findViewById(R.id.btnlogin);
        Button btn_create = (Button) findViewById(R.id.btnCreate);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_login.this,"đăng ký tài khoản",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity_login.this,activity_Register.class);
                startActivities(intent);
            }
        });
        //admin

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity_login.this);
                dialog.setTitle("xử lý");
                dialog.setCancelable(false);
                if(username.getText().length() !=0 && password.getText().length() !=0)
                {
                    if(username.getText().toString().equals("admin")&&password.getText().toString().equals("admin"))
                    {

                        Toast.makeText(activity_login.this,"Đăng Nhập Thành Công ",Toast.LENGTH_SHORT).show();
                        //--Intent dùng để chuyển từ trang hiện tại qua trang muốn đến (hiên tại.this,muốn đến.class)
                        //Intent intent = new Intent(activity_login.this,trang chủ);
                        //startActivities(intent);
                    }
                    else {

                        Toast.makeText(activity_login.this,"Đăng Nhập Thất Bại ",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(activity_login.this,"bạn cần nhập đủ thông tin",Toast.LENGTH_SHORT).show();

                }


            }
        });



    }


}
