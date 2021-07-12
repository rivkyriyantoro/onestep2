package com.example.onestep;

import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Date;

// Digunakan untuk menghubungkan ke activty  cl

public class Member extends AppCompatActivity {

    EditText formNama, formDesc;
    Button btnSave, btnDelete;
// Di gunakan untuk masuk ke layout member onestep dan dapat memilih edit atau hapus data member
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        ActionBar actionBar = getSupportActionBar();
        formNama = findViewById(R.id.formNama);
        formDesc = findViewById(R.id.formDesc);
        btnDelete =findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);
        btnDelete.setText(getIntent().getStringExtra("button"));

        if (getIntent().getStringExtra("action").equals("edit")) {
            actionBar.setTitle("Member Onestep");
            formNama.setText(getIntent().getStringExtra("title"));
            formDesc.setText(getIntent().getStringExtra("description"));
        } else {
            actionBar.setTitle("Member");
        }
// Digunakan untuk menabah keterangan waktu setelah berhasil menyimpan data member baru
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                String currentDate = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(date);

                DatabaseHelper database = new DatabaseHelper(Member.this);
                if (getIntent().getStringExtra("action").equals("edit")) {
                    database.edit(getIntent().getStringExtra("id"),formNama.getText().toString().trim(), formDesc.getText().toString().trim(), currentDate);
                } else {
                    database.insert(formNama.getText().toString().trim(), formDesc.getText().toString().trim(), currentDate);
                }
                finish();
            }

        });

        //Digunakan untuk masuk ke layout member

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().getStringExtra("action").equals("edit")) {
                    modal();
                } else {
                    finish();
                }
            }
        });
    }

    //Digunakan untuk pilihan jika ingin menghapus data member yang sudah ada
    void modal(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
      
        builder.setMessage("Apakah anda ingin menghapus " + getIntent().getStringExtra("title") + " ?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper database = new DatabaseHelper(Member.this);
                database.delete(getIntent().getStringExtra("id"));
                finish();
            }
        });
        // Di gunakan untuk kembali masuk ke layout ke member jika memilih tidak
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Nothing
            }
        });
        builder.create().show();
    }
}


