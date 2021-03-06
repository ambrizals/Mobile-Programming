package com.ambrizals.wallme;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class tambah_pengeluaran extends AppCompatActivity {
    EditText et_nama_pengeluaran;
    EditText et_jumlah_pengeluaran;
    dbControl dbControl;
    Button btn_tambah_pengeluaran;
    Button btn_batal;
    Cursor cursor;
    Helpers helpers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengeluaran);
        setTitle("Wallme - Tambah Pengeluaran");
        dbControl = new dbControl(this);
        helpers = new Helpers();

        et_nama_pengeluaran = (EditText)findViewById(R.id.et_nama_pengeluaran);
        et_jumlah_pengeluaran = (EditText)findViewById(R.id.et_jumlah_pengeluaran);
        btn_tambah_pengeluaran = (Button)findViewById(R.id.btn_tambah_pengeluaran);
        btn_batal = (Button)findViewById(R.id.btn_batal_2);

        btn_tambah_pengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((et_nama_pengeluaran.getText().toString().equals("")) || (et_jumlah_pengeluaran.getText().toString().equals("")) ){
                    Toast.makeText(tambah_pengeluaran.this, "Nama dan Jumlah Pengeluaran Wajib di Isi !", Toast.LENGTH_SHORT).show();
                } else {

                    //Input data
                    String queryInsert;
                    SQLiteDatabase conDB = dbControl.getWritableDatabase();
                    queryInsert = "insert into pengeluaran (nama_pengeluaran, jumlah_pengeluaran, created_at, updated_at)" +
                            " values ('" + et_nama_pengeluaran.getText().toString() + "', '" + et_jumlah_pengeluaran.getText().toString() + "" +
                            "', '" + helpers.todayDate() + "', '" + helpers.todayDate() + "')";
                    conDB.execSQL(queryInsert);

                    //Update saldo
                    String querySaldo = "SELECT * FROM SALDO";
                    SQLiteDatabase konDB = dbControl.getReadableDatabase();
                    cursor = konDB.rawQuery(querySaldo, null);
                    if (cursor.moveToFirst()) {
                        String saldo = cursor.getString(1);
                        Integer saldoAkhir = Integer.valueOf(saldo) - Integer.valueOf(et_jumlah_pengeluaran.getText().toString());
                        dbControl.updateSaldo(saldoAkhir.toString());
                    }
                    conDB.close();


                    Toast.makeText(tambah_pengeluaran.this, "Pengeluaran Berhasil Ditambah", Toast.LENGTH_SHORT).show();
                    PengeluaranListActivity.plrAct.runProperty();
                    finish();
                }
            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
//    @Override
//    public void onBackPressed(){
//        Intent toPengeluaran =  new Intent(tambah_pengeluaran.this, PengeluaranListActivity.class);
//        startActivity(toPengeluaran);
//        finish();
//    }
}
