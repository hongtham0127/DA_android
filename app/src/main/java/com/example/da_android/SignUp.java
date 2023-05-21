package com.example.da_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_android.model.User;


public class SignUp extends AppCompatActivity {
    EditText txtUsername, txtPassword, txtXacnhan_mk;
    Button btn_sign_up, btn_login;
    Context context;

    DB db = new DB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        context = this;

        txtUsername = findViewById(R.id.txt_cre_username);
        txtPassword = findViewById(R.id.password);
        txtXacnhan_mk = findViewById(R.id.xacnhan_mk);
        btn_sign_up = findViewById(R.id.btnCreate_Account);
        btn_login = findViewById(R.id.btnLogin_Account);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUp.this,"Đăng nhập tài khoản",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Login.class);
                startActivity(intent);
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username, password, xacnhan_mk;
                username = String.valueOf(txtUsername.getText());
                password = String.valueOf(txtPassword.getText());
                xacnhan_mk = String.valueOf(txtXacnhan_mk.getText());
                if (!username.equals("") && !password.equals("") && !xacnhan_mk.equals("") && password.equals(xacnhan_mk))
                {
                    User user = new User(username, password);
                    db.checkUserExistence(user, new DB.onCheckUserExistenceListener() {
                        @Override
                        public void onResult(boolean isExist) {
                            if (isExist) {
                                Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                            } else {
                                db.addUser(user);
                                Toast.makeText(getApplicationContext(), "THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}