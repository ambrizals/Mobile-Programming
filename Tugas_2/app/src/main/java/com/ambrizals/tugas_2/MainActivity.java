package com.ambrizals.tugas_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnSewa;
    Button btnLingkaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSewa = (Button)findViewById(R.id.btn_act_sewa);
        btnSewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sewaPage = new Intent(MainActivity.this, SewaActivity.class);
                startActivity(sewaPage);
            }
        });

        btnLingkaran = (Button)findViewById(R.id.btn_act_lingkaran);
        btnLingkaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lingkaranPage = new Intent(MainActivity.this, LingkaranActivity.class);
                startActivity(lingkaranPage);
            }
        });
    }
}
