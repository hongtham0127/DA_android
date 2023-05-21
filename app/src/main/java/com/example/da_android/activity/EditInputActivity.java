package com.example.da_android.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.da_android.MainActivity;
import com.example.da_android.R;
import com.example.da_android.fragment.EditInputFragment;
import com.example.da_android.model.Transaction;

public class EditInputActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_input);
        context = this;
        Transaction itemTransaction = (Transaction) getIntent().getSerializableExtra("itemTransaction");

        ImageButton btnBack = findViewById(R.id.btn_edit_back);
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.frame_container_edit_input, new EditInputFragment(itemTransaction))
                .commit();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
                String station = sharedPref.getString("station", null);
                if(station != null)
                {
                    Intent intent = new Intent(context, SearchTransactionActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}