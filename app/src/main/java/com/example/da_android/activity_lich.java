package com.example.da_android;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class activity_lich  extends AppCompatActivity {
    Button btn_lich;
    Calendar lich = Calendar.getInstance();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_header1);

        Button a;

        btn_lich = findViewById(R.id.btn_thoigian);
        btn_lich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(activity_lich.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Toast.makeText(getApplication(),day +"___"+month+"___"+year,Toast.LENGTH_SHORT).show();
                            }
                        },
                        lich.get(Calendar.YEAR),
                        lich.get(Calendar.MONTH),
                        lich.get(Calendar.DAY_OF_MONTH)
                        );
                dialog.show();;
            }
        });

    }
}
