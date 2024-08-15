package com.example.projectakhirjmp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button btnlogin,btnregister;
    EditText eduserlogin, edpasslogin;
    DatabaseHelper dblogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        btnlogin = findViewById(R.id.btnLogin);
        btnregister = findViewById(R.id.btnregisteronlogin);
        eduserlogin  = findViewById(R.id.usernameonlogin);
        edpasslogin = findViewById(R.id.passwordonlogin);
        dblogin = new DatabaseHelper(this);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = eduserlogin.getText().toString();
                String spassword = edpasslogin.getText().toString();
                Boolean checkUserPassword = dblogin.checkUserPassword(user, spassword);
                if (checkUserPassword) {
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }  else{
                    Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }

        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




    }
}