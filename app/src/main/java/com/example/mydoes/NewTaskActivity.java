package com.example.mydoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class NewTaskActivity extends AppCompatActivity {
    TextView titlepage, addtitle, adddesc, adddate;
    EditText titledoes, descdoes, datedoes;
    Button btnSaveTask, btnCancel;

    DatabaseReference databaseReference;
    Integer doesNum = new Random().nextInt();
    String keydoes = Integer.toString(doesNum);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        titlepage = findViewById(R.id.titlepage);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);

        titledoes = findViewById(R.id.titledoes);
        descdoes = findViewById(R.id.descdoes);
        datedoes = findViewById(R.id.datedoes);

        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Insert data to database
                databaseReference = FirebaseDatabase.getInstance().getReference().child("DoesApp").child("Does"+ doesNum);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Store data to database
                        dataSnapshot.getRef().child("titledoes").setValue(titledoes.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(descdoes.getText().toString());
                        dataSnapshot.getRef().child("datedoes").setValue(datedoes.getText().toString());
                        dataSnapshot.getRef().child("keydoes").setValue(keydoes);

                        Log.i("patricia", "works");

                        Intent a = new Intent(NewTaskActivity.this, MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent exit = new Intent(NewTaskActivity.this, MainActivity.class);
                startActivity(exit);
            }
        });

        // Import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        // Customize font
        titlepage.setTypeface(MMedium);

        addtitle.setTypeface(MLight);
        titledoes.setTypeface(MMedium);

        adddesc.setTypeface(MLight);
        descdoes.setTypeface(MMedium);

        adddate.setTypeface(MLight);
        datedoes.setTypeface(MMedium);

        btnSaveTask.setTypeface(MMedium);
        btnCancel.setTypeface(MLight);
    }
}