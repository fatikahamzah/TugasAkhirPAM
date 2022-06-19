package com.example.tugasakhirpam;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NotifikasiPelanggan extends AppCompatActivity {

    EditText jmlBarang;
    TextView total;
    Button confirm,update;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi_pelanggan); // agar layout kedua bisa dipanggil atau muncul

        jmlBarang = findViewById(R.id.JumlahBarangconf);
        total = findViewById(R.id.totalHarga);
        update = findViewById(R.id.butUpdate);

        /*set jml and price from before*/
        Bundle bundle = getIntent().getExtras();
        jmlBarang.setText(bundle.getString("jumlahbarang"));

        String harga = bundle.getString("harga");
        /*notelp as a doc data*/
        String notelp = bundle.getString("notelp");

        String jmlupdate = jmlBarang.getText().toString();
        Integer totalharga = Integer.parseInt(jmlupdate)*Integer.parseInt(harga);

        /*total harga*/
        total.setText("Total   :  "+totalharga.toString());
        /*tombol update*/
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference doreef = db.collection("Users").document(notelp);
                doreef
                        .update("Jumlah", jmlBarang.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                String jmlupdate = jmlBarang.getText().toString();
                                Integer totalharga = Integer.parseInt(jmlupdate)*Integer.parseInt(harga);
                                total.setText("Total   :  "+totalharga);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }
        });


        /*confirm*/
        confirm = findViewById(R.id.buttonConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference doreef = db.collection("Users").document(notelp);
                        doreef
                        .update("Jumlah", jmlBarang.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                String jmlupdate = jmlBarang.getText().toString();
                                Integer totalharga = Integer.parseInt(jmlupdate)*Integer.parseInt(harga);
                                total.setText("Total   :  "+totalharga);
                                bundle.putString("notelp",notelp);
                                Intent i = new Intent(getApplicationContext(),PesananDikirim.class);
                                i.putExtras(bundle);
                                startActivity(i);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }
        });

    }
}