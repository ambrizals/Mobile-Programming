package com.ambrizals.uts15101563;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InputNilaiActivity extends AppCompatActivity {
    EditText et_nilaiTugas;
    EditText et_nilaiUTS;
    EditText et_nilaiUAS;
    Button btn_hitungSubmit;
    String gradeResult;
    Double nilaiTugas, nilaiUTS, nilaiUAS, nilaiTotal;
    Integer hasilAkhir;
    TextView tv_grade_result;
    TextView tv_nilai_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_nilai);

        btn_hitungSubmit = (Button)findViewById(R.id.btn_hitungGrade);
        btn_hitungSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_nilaiTugas = (EditText)findViewById(R.id.et_nilai_tugas);
                et_nilaiUTS = (EditText)findViewById(R.id.et_nilai_uts);
                et_nilaiUAS = (EditText)findViewById(R.id.et_nilai_uas);

                if ( (et_nilaiUAS.getText().toString().equals("")) || (et_nilaiUTS.getText().toString().equals("")) || (et_nilaiUAS.getText().toString().equals(""))  ) {
                    Toast.makeText(InputNilaiActivity.this, "Terdapat inputan yang belum terisi", Toast.LENGTH_SHORT).show();
                } else {
                    nilaiTugas = Double.parseDouble(et_nilaiTugas.getText().toString());
                    nilaiUTS = Double.parseDouble(et_nilaiUTS.getText().toString());
                    nilaiUAS = Double.parseDouble(et_nilaiUAS.getText().toString());

                    nilaiTotal = (0.3 * nilaiTugas) + (0.35 * nilaiUTS) + (0.35 * nilaiUAS);

                    hasilAkhir = (int)Math.round(nilaiTotal);
                    if (hasilAkhir >= 80) {
                        gradeResult = "A";
                    } else if ((hasilAkhir <= 79) && (hasilAkhir >= 65)) {
                        gradeResult = "B";
                    } else if ((hasilAkhir <= 64) && (hasilAkhir >= 55)) {
                        gradeResult = "C";
                    } else if ((hasilAkhir <= 54) && (hasilAkhir >= 40)) {
                        gradeResult = "D";
                    } else if (hasilAkhir <= 40) {
                        gradeResult = "E";
                    } else {
                        gradeResult = "XX";
                    }

                    tv_grade_result = (TextView)findViewById(R.id.tv_grade_result);
                    tv_nilai_result = (TextView)findViewById(R.id.tv_nilai_result);

                    tv_grade_result.setText(gradeResult);
                    tv_nilai_result.setText(hasilAkhir.toString());
//                    Toast.makeText(InputNilaiActivity.this, hasilAkhir.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
