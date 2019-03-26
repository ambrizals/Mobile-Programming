package com.ambrizals.tugas_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MahasiswaActivity extends AppCompatActivity {

    Button btnMhsShow;
    EditText etMhsNama;
    EditText etMhsNim;
    TextView resultMhsNama;
    TextView resultMhsNim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        btnMhsShow = (Button)findViewById(R.id.btn_mhs_tampil);
        btnMhsShow.setOnClickListener(new View.OnClickListener() {
//            String nama, nim;
            @Override
            public void onClick(View v) {
                etMhsNama = (EditText)findViewById(R.id.et_mhs_nama);
                etMhsNim = (EditText)findViewById(R.id.et_mhs_nim);
                if (etMhsNama.getText().toString().equals("") || etMhsNim.getText().toString().equals("")) {
                    Toast.makeText(MahasiswaActivity.this, "Terdapat inputan yang belum terisi !", Toast.LENGTH_SHORT).show();
                } else {
                    resultMhsNama = (TextView)findViewById(R.id.tv_result_namaMhs);
                    resultMhsNim = (TextView)findViewById(R.id.tv_result_nimMhs);

                    resultMhsNama.setText(etMhsNama.getText().toString());
                    resultMhsNim.setText(etMhsNim.getText().toString());
                }
            }
        });
    }
}
