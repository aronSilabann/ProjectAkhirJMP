package com.example.projectakhirjmp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {
    Button btnlogin,btnregister;
    EditText eduser, edpass;
    DatabaseHelper dblogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        btnlogin = findViewById(R.id.login);
        btnregister = findViewById(R.id.register);
        eduser = findViewById(R.id.username);
        edpass = findViewById(R.id.password);
        dblogin = new DatabaseHelper(this);

      btnregister.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String user = eduser.getText().toString();
              String password = edpass.getText().toString();
              Boolean checkuser = dblogin.checkUser(user);
              if (checkuser == false) {
                  Boolean insert = dblogin.insertUser(user, password);
                  if (insert == true) {
                      Toast.makeText(Register.this, "Register Successful", Toast.LENGTH_SHORT).show();
                      finish();
                  } else {
                      Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                  }
              } else {
                  Toast.makeText(Register.this, "User Already Exists", Toast.LENGTH_SHORT).show();
              };};
      });
      btnlogin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(Register.this, Login.class);
              startActivity(intent);
          }
      });
    };
}