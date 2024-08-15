package com.example.projectakhirjmp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Calendar;

public class InputData extends AppCompatActivity {
    Button btninputedit;
    EditText ednama, edumur, edalamat, edjkel, edtgllahir;
    DatabaseHelper dbmaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input_data);

        btninputedit = findViewById(R.id.btninputdata);
        ednama = findViewById(R.id.masukkannama);
        edumur = findViewById(R.id.masukkanumur);
        edtgllahir = findViewById(R.id.masukkantgllahir);
        edalamat = findViewById(R.id.masukkanalamat);
        edjkel = findViewById(R.id.masukkanjeniskelamin);
        dbmaster = new DatabaseHelper(this);

        // Set listener untuk EditText tanggal lahir
        edtgllahir.setOnClickListener(v -> showDatePickerDialog());

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        if (id != null) {
            Cursor data = dbmaster.getAllData();
            if (data.moveToFirst()) {
                do {
                    if (data.getString(0).equals(id)) {
                        ednama.setText(data.getString(1));
                        edumur.setText(data.getString(2));
                        edtgllahir.setText(data.getString(3));
                        edjkel.setText(data.getString(4));
                        edalamat.setText(data.getString(5));
                        btninputedit.setText("Update Data");
                        break;
                    }
                } while (data.moveToNext());
            }

            btninputedit.setOnClickListener(v -> {
                boolean isUpdated = dbmaster.updateData(id, ednama.getText().toString(), Integer.parseInt(edumur.getText().toString()), edtgllahir.getText().toString(), edjkel.getText().toString(), edalamat.getText().toString());
                if (isUpdated)
                    Toast.makeText(InputData.this, "Data Updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(InputData.this, "Data Not Updated", Toast.LENGTH_LONG).show();
                finish();
            });
        } else {
            btninputedit.setOnClickListener(v -> {
                boolean isInserted = dbmaster.insertData(ednama.getText().toString(), Integer.parseInt(edumur.getText().toString()), edtgllahir.getText().toString(), edjkel.getText().toString(), edalamat.getText().toString());
                if (isInserted)
                    Toast.makeText(InputData.this, "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(InputData.this, "Data Not Inserted", Toast.LENGTH_LONG).show();
                finish();
            });
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Update EditText dengan tanggal yang dipilih
            edtgllahir.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
        }, year, month, day);

        datePickerDialog.show();
    }
}
