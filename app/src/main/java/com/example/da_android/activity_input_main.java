package com.example.da_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class activity_input_main extends AppCompatActivity {
    Button btn_lich;

    Calendar lich = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_main);

        btn_lich = findViewById(R.id.btn_thoigian);
        btn_lich.setText(lich.get(Calendar.DAY_OF_MONTH) + "/" +(lich.get(Calendar.MONTH)+1) + "/" +lich.get(Calendar.YEAR));
        btn_lich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(activity_input_main.this,
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