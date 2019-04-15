package com.ambrizals.tugas_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SewaActivity extends AppCompatActivity {
    private Button btnSewaSubmit;
    private Button btnSewaReset;
    private EditText etLamaSewa;
    private TextView tvResultSewa;
    private RadioGroup rgSewaType;
    private RadioButton jenisSewa;
    private LinearLayout resultSewa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa);

        btnSewaSubmit = (Button)findViewById(R.id.btn_submitSewa);
        btnSewaSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int biayaSewa, jumlahSewa;
                rgSewaType = (RadioGroup)findViewById(R.id.rg_sewaType);
                int selectedSewaType = rgSewaType.getCheckedRadioButtonId();

                jenisSewa = (RadioButton)findViewById(selectedSewaType);
                etLamaSewa = (EditText)findViewById(R.id.et_lama_sewa);

//                Toast.makeText(SewaActivity.this,String.valueOf(selectedSewaType),Toast.LENGTH_SHORT).show();

                if ((selectedSewaType == -1) || (etLamaSewa.getText().toString().equals(""))) {
                    Toast.makeText(SewaActivity.this,"Terdapat inputan yang belum di isi !", Toast.LENGTH_SHORT).show();
                } else {
                    switch(jenisSewa.getText().toString()) {
                        case "Standar":
                            biayaSewa = 200;
                            break;

                        case "Ekonomi":
                            biayaSewa = 100;
                            break;

                        case "Premium":
                            biayaSewa = 400;
                            break;

                        default:
                            biayaSewa = 0;
                            break;
                    }

                    jumlahSewa = biayaSewa * Integer.parseInt(etLamaSewa.getText().toString());

                    tvResultSewa = (TextView)findViewById(R.id.tv_result_sewa);
                    tvResultSewa.setText(String.valueOf(jumlahSewa));


                    resultSewa = (LinearLayout)findViewById(R.id.result_sewa);
                    if (resultSewa.getVisibility() == View.INVISIBLE) {
                        resultSewa.setVisibility(View.VISIBLE);
                    }
                 }

            }
        });

        btnSewaReset = (Button)findViewById(R.id.btn_reset);
        btnSewaReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rgSewaType = (RadioGroup)findViewById(R.id.rg_sewaType);
                int selectedSewaType = rgSewaType.getCheckedRadioButtonId();
                jenisSewa = (RadioButton)findViewById(selectedSewaType);
                etLamaSewa = (EditText)findViewById(R.id.et_lama_sewa);

//                Toast.makeText(SewaActivity.this,String.valueOf(selectedSewaType),Toast.LENGTH_SHORT).show();

                if (selectedSewaType == -1) {
                    etLamaSewa.setText("");
                } else {
                    jenisSewa.setChecked(false);
                    etLamaSewa.setText("");
                }

            }
        });
    }
}
