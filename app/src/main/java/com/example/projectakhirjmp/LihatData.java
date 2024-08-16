package com.example.projectakhirjmp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
public class LihatData extends AppCompatActivity {
    Button buttontambahdata;
    DatabaseHelper myDb;
    ListView listView;
    ArrayList<String> listData;
    ArrayAdapter<String> adapter;
    EditText editTextSearch;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);

        buttontambahdata = findViewById(R.id.btntambahdata);
        listView = findViewById(R.id.listview1);
        editTextSearch = findViewById(R.id.editcari);
        myDb = new DatabaseHelper(this);
        listData = new ArrayList<>();

        loadData();

        // Setup adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        // Pencarian data
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Tambah data
        buttontambahdata.setOnClickListener(v -> {
            Intent intent = new Intent(LihatData.this, InputData.class);
            startActivity(intent);
        });

        // Long click untuk opsi lihat, update, atau hapus data
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            String itemId = getIdByName(selectedItem);

            showOptionsDialog(itemId);

        });
    }

    private void loadData() {
        listData.clear();
        Cursor data = myDb.getAllData();
        if (data.getCount() == 0) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                listData.add(data.getString(1)); // Hanya menampilkan nama
            }
        }
    }

    // Mengambil ID dari nama
    private String getIdByName(String name) {
        Cursor data = myDb.getAllData();
        while (data.moveToNext()) {
            if (data.getString(1).equals(name)) {
                return data.getString(0); // Mengembalikan ID dari nama yang cocok
            }
        }
        return null;
    }

    private void showOptionsDialog(String itemId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LihatData.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_options, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        Button btnLihatData = dialogView.findViewById(R.id.btnLihatData);
        Button btnUpdateData = dialogView.findViewById(R.id.btnUpdateData);
        Button btnHapusData = dialogView.findViewById(R.id.btnHapusData);

        btnLihatData.setOnClickListener(v -> {
            lihatData(itemId);
            dialog.dismiss();
        });

        btnUpdateData.setOnClickListener(v -> {
            updateData(itemId);
            dialog.dismiss();
        });

        btnHapusData.setOnClickListener(v -> {
            hapusData(itemId);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void lihatData(String itemId) {
        Intent intent = new Intent(LihatData.this, tampildata.class);
        intent.putExtra("ID", itemId);
        startActivity(intent);
    }

    private void updateData(String itemId) {
        Intent intent = new Intent(LihatData.this, InputData.class);
        intent.putExtra("ID", itemId);
        startActivity(intent);
    }

    private void hapusData(String itemId) {
        Integer deletedRows = myDb.deleteData(itemId);
        if (deletedRows > 0) {
            Toast.makeText(LihatData.this, "Data Deleted", Toast.LENGTH_LONG).show();
            loadData();  // Refresh list setelah penghapusan
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(LihatData.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
        }
    }
}
