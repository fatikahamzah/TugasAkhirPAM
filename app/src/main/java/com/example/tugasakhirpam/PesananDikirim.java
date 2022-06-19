package com.example.tugasakhirpam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class PesananDikirim extends AppCompatActivity {

    Button batalbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanandikirim);
        batalbutton = findViewById(R.id.batalbut);

        batalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                String datanotelp = bundle.getString("notelp");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Users").document(datanotelp).delete();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


    }
}