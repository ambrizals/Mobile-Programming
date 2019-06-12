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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class tambah_pemasukan extends AppCompatActivity {

    EditText et_nama_pemasukan;
    EditText et_jumlah_pemasukan;
    Button btn_tambah_pemasukan;
    Button btn_batal;
    dbControl dbControl;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pemasukan);
        setTitle("Wallme - Tambah Pemasukan");
        dbControl = new dbControl(this);
        et_nama_pemasukan = (EditText)findViewById(R.id.et_nama_pemasukan);
        et_jumlah_pemasukan = (EditText)findViewById(R.id.et_jumlah_pemasukan);
        btn_tambah_pemasukan = (Button)findViewById(R.id.btn_tambah_pemasukan);
        btn_batal = (Button)findViewById(R.id.btn_batal_1);

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(tambah_pemasukan.this, MainActivity.class);
                startActivity(toMain);
                finish();
            }
        });

        btn_tambah_pemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((et_nama_pemasukan.getText().toString().equals("")) || (et_jumlah_pemasukan.getText().toString().equals(""))) {
                    Toast.makeText(tambah_pemasukan.this, "Nama Pemasukan dan Jumlah Pemasukan Wajib di Isi !", Toast.LENGTH_SHORT).show();
                } else {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS.SSS");
                    Date date = new Date();
                    String tanggalSekarang = dateFormat.format(date);

                    //Input data
                    String queryInsert;
                    SQLiteDatabase conDB = dbControl.getWritableDatabase();
                    queryInsert = "insert into pemasukan (nama_pemasukan, jumlah_pemasukan, created_at, updated_at)" +
                            " values ('" + et_nama_pemasukan.getText().toString() + "', '" + et_jumlah_pemasukan.getText().toString() + "" +
                            "', '" + tanggalSekarang + "', '" + tanggalSekarang + "')";
                    conDB.execSQL(queryInsert);

                    //Update saldo
                    String queryUpdate;
                    String querySaldo = "SELECT * FROM SALDO";
                    SQLiteDatabase konDB = dbControl.getReadableDatabase();
                    cursor = konDB.rawQuery(querySaldo, null);
                    if (cursor.moveToFirst()) {
                        String saldo = cursor.getString(1);
                        Integer saldoAkhir = Integer.valueOf(saldo) + Integer.valueOf(et_jumlah_pemasukan.getText().toString());
                        queryUpdate = "UPDATE saldo set jumlah_saldo = '" + saldoAkhir.toString() + "' where id_saldo = '1'";
                        conDB.execSQL(queryUpdate);
                    }

                    conDB.close();
                    Toast.makeText(tambah_pemasukan.this, "Pemasukan Berhasil Ditambah", Toast.LENGTH_SHORT).show();
                    Intent toMain = new Intent(tambah_pemasukan.this, MainActivity.class);
                    startActivity(toMain);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent toMain = new Intent(tambah_pemasukan.this, MainActivity.class);
        startActivity(toMain);
        finish();
    }

}
