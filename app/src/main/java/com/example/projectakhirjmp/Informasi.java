package com.example.projectakhirjmp;


import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Informasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi);

        // Mendapatkan referensi ke TextView
        TextView informasiTextView = findViewById(R.id.informasiTextView);

        // Menetapkan teks informasi
        String informasi = "Selamat datang Aplikasi OSBORN\n\n" +
                "Aplikasi ini dibuat untuk membantu Pendataan costumer. \n" +
                "OSBORN bergerak dalam bidang Farmasi \n" +
                "Kami harap Pelayanan kami menjadi kepuasan anda!";

        // Menampilkan informasi pada TextView
        informasiTextView.setText(informasi);
    }
}