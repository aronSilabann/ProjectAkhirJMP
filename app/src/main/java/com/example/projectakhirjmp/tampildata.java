package com.example.projectakhirjmp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class tampildata extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView textViewNama, textViewUmur, textViewTanggalLahir, textViewJenisKelamin, textViewAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampildata);

        textViewNama = findViewById(R.id.textViewNama);
        textViewUmur = findViewById(R.id.textViewUmur);
        textViewTanggalLahir = findViewById(R.id.textViewTanggalLahir);
        textViewJenisKelamin = findViewById(R.id.textViewJenisKelamin);
        textViewAlamat = findViewById(R.id.textViewAlamat);
        myDb = new DatabaseHelper(this);

        String itemId = getIntent().getStringExtra("ID");
        loadData(itemId);
    }

    private void loadData(String itemId) {
        Cursor data = myDb.getDataById(itemId);
        if (data.moveToFirst()) {
            textViewNama.setText("Nama : "+data.getString(1));
            textViewUmur.setText("Umur : "+data.getString(2));
            textViewTanggalLahir.setText("Tanggal Lahir : "+data.getString(3));
            textViewJenisKelamin.setText("Jenis Kelamin : "+data.getString(4));
            textViewAlamat.setText("Alamat : "+data.getString(5));
        }
    }
}
