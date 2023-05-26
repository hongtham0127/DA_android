package com.example.da_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.da_android.model.User;

public class Login extends AppCompatActivity {
    Context context;
    DB db = new DB();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        TextView txt_username = (TextView) findViewById(R.id.txt_username);
        TextView txt_password = (TextView) findViewById(R.id.txt_password);
        TextView forgotPass = (TextView) findViewById(R.id.forgot_password);

        Button btn_login = (Button) findViewById(R.id.btnlogin);
        Button btn_create = (Button) findViewById(R.id.btnCreate);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this,"đăng ký tài khoản",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SignUp.class);
                startActivity(intent);
            }
        });
        //admin
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username, password;
                username = String.valueOf(txt_username.getText());
                password = String.valueOf(txt_password.getText());
                if(!username.equals("") && !password.equals(""))
                {
                    User user = new User(username, password);
                    db.loginUser(user, new DB.onCheckUserExistenceListener() {
                        @Override
                        public void onResult(boolean isExist) {
                            if (isExist) {
                                Toast.makeText(Login.this,"Đăng Nhập Thành Công",Toast.LENGTH_SHORT).show();

                                SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("username", username);
                                editor.apply();
                                db.addAllCategory();

                                Intent intent = new Intent(context,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(Login.this,"Đăng Nhập Thất Bại ",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Login.this,"Vui lòng nhập đủ thông tin",Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EmailSenderActivity.class);
                startActivity(intent);
            }
        });
    }
}
