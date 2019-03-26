package com.ambrizals.tugas_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnMhs;
    Button btnHt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMhs = (Button)findViewById(R.id.btn_mahasiswa);
        btnMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mhsPage = new Intent(MainActivity.this, MahasiswaActivity.class);
                startActivity(mhsPage);
            }
        });

        btnHt = (Button)findViewById(R.id.btn_hitung);
        btnHt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent htPages = new Intent(MainActivity.this, HitungActivity.class);
                startActivity(htPages);
            }
        });
    }
}
