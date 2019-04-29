package com.ambrizals.uts15101563;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button btnHitung_Activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnHitung_Activity = (Button)findViewById(R.id.btn_hitungGrade);
        btnHitung_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inputNilai = new Intent(WelcomeActivity.this, InputNilaiActivity.class);
                startActivity(inputNilai);
            }
        });
    }
}
