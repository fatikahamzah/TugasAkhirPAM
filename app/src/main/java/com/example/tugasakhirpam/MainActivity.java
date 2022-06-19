package com.example.tugasakhirpam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button button; // variabel id yang akan digunakann
    TextView tvNamaBarang;// variabel id yang akan digunakann
    EditText JumlahBarang;// variabel id yang akan digunakan
    EditText NoTelp;
    EditText Alamat;// variabel id yang akan digunakann
    // jika tidak diberi id maka program akan bingung siapa yang akan dipanggil
    /*Image*/
    ImageView galon19,galon9;
    TextView tvgalon19L,tvgalon9L;
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Firabase Database*/
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        /*textview nama barang*/
        tvgalon19L = findViewById(R.id.Galon19Liter);
        tvgalon9L = findViewById(R.id.Galon9Liter);
        /*gambar untuk ketika onclick*/
        galon9 = findViewById(R.id.imgGalon9);
        galon19 = findViewById(R.id.imgGalon19);

        JumlahBarang = findViewById(R.id.JumlahBarang);//  manggil id agar bisa digunakan
        NoTelp = findViewById(R.id.NoTelp);
        Alamat = findViewById(R.id.Alamat); //  manggil id agar bisa digunakan
        // jika tidak diberi code di atas maka jika kita mengisi pesanan tidak akan bisa dan aplikasi akan force close
        tvNamaBarang = findViewById(R.id.tvNamaBarang);

        /*onclick pada gambar*/
        galon19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvNamaBarang.setText("Nama Barang : "+tvgalon19L.getText().toString());
            }
        });

        galon9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvNamaBarang.setText("Nama Barang : "+tvgalon9L.getText().toString());
            }
        });

        /*onclick pada gambar*/
        button =  findViewById(R.id.butPesan);
        button.setOnClickListener(new View.OnClickListener() { // agar button berfungsi dan bisa digunakan
            @Override
            public void onClick(View v) {
                if(tvNamaBarang.getText().toString().equals("Nama Barang")){
                    Toast.makeText(MainActivity.this, "Silahkan Memilih Galon", Toast.LENGTH_SHORT).show();
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("jumlahbarang",JumlahBarang.getText().toString());
                    if(tvNamaBarang.equals(tvgalon9L.getText().toString())){
                        bundle.putString("harga","15000");
                    }else{
                        bundle.putString("harga","20000");
                    }
                    bundle.putString("notelp",NoTelp.getText().toString());
                    Intent i = new Intent(getApplicationContext(), NotifikasiPelanggan.class);
                    i.putExtras(bundle);
                    /*Map itu berguna untuk membungkus data yang telah diInput oleh pengguna
                    * dan akan disimpan kedalam database*/
                    Map<String, Object> user = new HashMap<>();
                    user.put("Jumlah", JumlahBarang.getText().toString());
                    user.put("NoTelp", NoTelp.getText().toString());
                    user.put("Alamat", Alamat.getText().toString());

                    /*Memasukkan data kedalam database dengan collection Users
                    * dan Nama document menggunakan notelp*/
                    db.collection("Users")
                            .document(NoTelp.getText().toString())
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "DocumentSnapshot Success");

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                    startActivity(i);// agar aplikasi berkerja berpindah layout kedua
                }
            }
        });


    }
}