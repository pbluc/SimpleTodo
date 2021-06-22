package com.example.mydoes;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView titlePage, subtitlePage, endPage;
    Button btnAddNew;

    DatabaseReference databaseReference;
    RecyclerView ourDoes;
    ArrayList<MyDoes> list;
    DoesAdapter doesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlePage = findViewById(R.id.titlePage);
        subtitlePage = findViewById(R.id.subtitlePage);
        endPage = findViewById(R.id.endPage);

        btnAddNew = findViewById(R.id.btnAddNew);

        // Import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        // Customize font
        titlePage.setTypeface(MMedium);
        subtitlePage.setTypeface(MLight);
        endPage.setTypeface(MLight);

        btnAddNew.setTypeface(MLight);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivity(a);
            }
        });

        // Working with data
        ourDoes = findViewById(R.id.ourDoes);
        ourDoes.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyDoes>();

        // Get data from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DoesApp");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Set code to retrieve data and replace layout
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MyDoes p = dataSnapshot1.getValue(MyDoes.class);
                    list.add(p);
                }
                doesAdapter = new DoesAdapter(MainActivity.this, list);
                ourDoes.setAdapter(doesAdapter);
                doesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Set code to show an error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
