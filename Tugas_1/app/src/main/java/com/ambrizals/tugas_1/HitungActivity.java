package com.ambrizals.tugas_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HitungActivity extends AppCompatActivity {

    Button submitHitung;
    EditText etBill1;
    EditText etBill2;
    TextView resultHitung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung);

        submitHitung = (Button)findViewById(R.id.btn_hitung_submit);
        submitHitung.setOnClickListener(new View.OnClickListener() {
            Integer a,b,c;
            @Override
            public void onClick(View v) {
                etBill1 = (EditText)findViewById(R.id.et_bill_1);
                etBill2 = (EditText)findViewById(R.id.et_bill_2);
                if ((etBill1.getText().toString().equals("")) || (etBill2.getText().toString().equals(""))) {
                    Toast.makeText(HitungActivity.this, "Terdapat field yang belum di isi !", Toast.LENGTH_SHORT).show();
                } else {
                    a = Integer.parseInt(etBill1.getText().toString());
                    b = Integer.parseInt(etBill2.getText().toString());
                    c = a+b;
                    resultHitung = (TextView)findViewById(R.id.result_hitung);
                    resultHitung.setText(c.toString());
                }
            }
        });
    }
}
