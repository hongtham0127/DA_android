package com.example.da_android.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

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
                finish();
            }
        });
    }
}