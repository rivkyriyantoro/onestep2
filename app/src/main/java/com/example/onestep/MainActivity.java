package com.example.onestep;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper databaseHelper;
    ArrayList<String> id, title, description, date;
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    FloatingActionButton btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Member.class);
                intent.putExtra("action", "Baru");
                intent.putExtra("button", "Kembali");
                startActivityForResult(intent, 1);
            }
        });

        databaseHelper = new DatabaseHelper(MainActivity.this);
        id = new ArrayList<>();
        title = new ArrayList<>();
        description = new ArrayList<>();
        date = new ArrayList<>();

        storeDataInArray();

        recyclerAdapter = new RecyclerAdapter(MainActivity.this, this, id, title, description, date);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArray() {
        Cursor cursor = databaseHelper.read();
        if (cursor.getCount() == 0) {
            Toast.makeText(MainActivity.this, " Data  Kosong", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                title.add(cursor.getString(1));
                description.add(cursor.getString(2));
                date.add(cursor.getString(3));
            }
        }
    }
}



