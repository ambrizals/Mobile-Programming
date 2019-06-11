package com.ambrizals.wallme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ubah_pemasukan extends AppCompatActivity {
    String id_pemasukan;
    Integer saldo;
    Integer jumlah_pemasukan;
    EditText et_nama_pemasukan;
    EditText et_jumlah_pemasukan;
    Button btn_ubah_pemasukan;
    Button btn_batal;
    dbControl dbControl;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_pemasukan);
        dbControl = new dbControl(this);
        id_pemasukan = getIntent().getStringExtra("id_pmsk");
        // Init Variable
        et_nama_pemasukan = (EditText)findViewById(R.id.et_ub_nama_pemasukan);
        et_jumlah_pemasukan = (EditText)findViewById(R.id.et_ub_jumlah_pemasukan);
        btn_ubah_pemasukan = (Button)findViewById(R.id.btn_ubah_pemasukan);
        btn_batal = (Button)findViewById(R.id.btn_batal_3);

        // Panggil Data Saldo Dari SQlite
        String querySaldo = "SELECT * FROM SALDO";
        SQLiteDatabase conDB = dbControl.getReadableDatabase();
        cursor = conDB.rawQuery(querySaldo, null);
        if (cursor.moveToFirst()) {
            saldo = cursor.getInt(1);
        }

        // Panggil Data Dari SQlite
        SQLiteDatabase bacaData = dbControl.getReadableDatabase();
        cursor = bacaData.rawQuery("SELECT * FROM pemasukan WHERE id_pemasukan = '"+id_pemasukan+"'", null);
        cursor.moveToFirst();
        if (cursor.getCount() == 1) {
            // Set Data
            cursor.moveToPosition(0);
            et_nama_pemasukan.setText(cursor.getString(1));
            jumlah_pemasukan = Integer.valueOf(cursor.getString(2));
            et_jumlah_pemasukan.setText(cursor.getString(2));
        }
        //Button action
        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(ubah_pemasukan.this, MainActivity.class);
                startActivity(toMain);
                finish();
            }
        });

        btn_ubah_pemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((et_nama_pemasukan.getText().toString().equals("")) || (et_jumlah_pemasukan.getText().toString().equals(""))){
                    Toast.makeText(ubah_pemasukan.this, "Nama dan Jumlah Pemasukan tidak boleh kosong !", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase tulisData = dbControl.getWritableDatabase();
                    String queryUpdate = "UPDATE pemasukan set nama_pemasukan = '"+et_nama_pemasukan.getText().toString()+"', jumlah_pemasukan ='"+et_jumlah_pemasukan.getText().toString()+"' where id_pemasukan ='"+id_pemasukan+"'";
                    tulisData.execSQL(queryUpdate);

                    Integer compare = Integer.valueOf(et_jumlah_pemasukan.getText().toString());
                    Integer selisih = compare - jumlah_pemasukan;
                    Integer saldoAkhir;

                    saldoAkhir = saldo + selisih;
                    queryUpdate = "UPDATE saldo set jumlah_saldo = '" + saldoAkhir.toString() + "' where id_saldo = '1'";
                    tulisData.execSQL(queryUpdate);

                    Toast.makeText(ubah_pemasukan.this, "Data berhasil diubah !", Toast.LENGTH_SHORT).show();
                    Intent toMain = new Intent(ubah_pemasukan.this, MainActivity.class);
                    startActivity(toMain);
                    finish();

                }
            }
        });


    }
}
