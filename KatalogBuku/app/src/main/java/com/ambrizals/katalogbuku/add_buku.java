package com.ambrizals.katalogbuku;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_buku extends AppCompatActivity {

    EditText et_add_judul;
    EditText et_add_pengarang;
    EditText et_add_tahun;
    EditText et_add_penerbit;
    Button btn_add_buku;
    dbControl koneksiDB;

    private String judul, pengarang, tahun, penerbit, queryInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buku);

        et_add_judul = (EditText)findViewById(R.id.et_add_judulbuku);
        et_add_pengarang = (EditText)findViewById(R.id.et_add_pengarang);
        et_add_tahun = (EditText)findViewById(R.id.et_add_tahun);
        et_add_penerbit = (EditText)findViewById(R.id.et_add_penerbit);

        koneksiDB = new dbControl(this);

        btn_add_buku = (Button)findViewById(R.id.btn_add_buku);
        btn_add_buku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judul = et_add_judul.getText().toString();
                pengarang = et_add_pengarang.getText().toString();
                tahun = et_add_tahun.getText().toString();
                penerbit = et_add_penerbit.getText().toString();

                if ((judul.equals("")) || (pengarang.equals("")) || (tahun.equals("")) || (penerbit.equals(""))) {
                    Toast.makeText(add_buku.this, "Terdapat kolom yang belum dihapus", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase conDB = koneksiDB.getWritableDatabase();
                    queryInsert = "INSERT INTO buku " +
                            "(judul_buku, nama_pengarang, tahun_terbit, penerbit) " +
                            "VALUES " +
                            "('"+judul+"', '"+pengarang+"', '"+tahun+"', '"+penerbit+"')";
                    conDB.execSQL(queryInsert);

                    //Menampilkan main activity kembali
                    Toast.makeText(add_buku.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    Intent toMain = new Intent(add_buku.this, MainActivity.class);
                    startActivity(toMain);
                    finish();

                }
            }
        });
    }
}
