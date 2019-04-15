package com.ambrizals.tugas_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class LingkaranActivity extends AppCompatActivity {
    private EditText etJarijari;
    private EditText etDiameter;
    private EditText etResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lingkaran);

        etJarijari = (EditText)findViewById(R.id.et_jariLingkaran);
        etDiameter = (EditText)findViewById(R.id.et_diameterLingkaran);
        etResult = (EditText)findViewById(R.id.et_luasLingkaran);
        etJarijari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer hasil, jari;
                if (etJarijari.getText().toString().equals("")) {
                    etDiameter.setEnabled(true);
                    etResult.setText("");
                } else {
                    etDiameter.setEnabled(false);
                    jari = Integer.parseInt(etJarijari.getText().toString());
                    hasil = (22/7) * (jari * jari);
                    etResult.setText(String.valueOf(hasil));
                }
            }
        });

        etDiameter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer hasil, jari;
                if(etDiameter.getText().toString().equals("")) {
                    etJarijari.setEnabled(true);
                    etResult.setText("");
                } else {
                    etJarijari.setEnabled(false);
                    jari = Integer.parseInt(etDiameter.getText().toString()) / 2;
                    hasil = (22/7) * (jari * jari);
                    etResult.setText(String.valueOf(hasil));
                }
            }
        });
    }
}
