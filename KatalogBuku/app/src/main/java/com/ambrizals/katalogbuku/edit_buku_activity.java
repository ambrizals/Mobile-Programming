package com.ambrizals.katalogbuku;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class edit_buku_activity extends AppCompatActivity {
    protected Cursor cursor;
    EditText et_edit_buku_judul;
    EditText et_edit_buku_pengarang;
    EditText et_edit_buku_tahun;
    EditText et_edit_buku_penerbit;
    Button btn_edit_buku;
    dbControl koneksiDB;

    private String id,judul, pengarang, tahun, penerbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_buku_activity);

        koneksiDB = new dbControl(this);
        et_edit_buku_judul = (EditText)findViewById(R.id.et_edit_buku_judul);
        et_edit_buku_pengarang = (EditText)findViewById(R.id.et_edit_buku_pengarang);
        et_edit_buku_tahun = (EditText)findViewById(R.id.et_edit_buku_tahun);
        et_edit_buku_penerbit = (EditText)findViewById(R.id.et_edit_buku_penerbit);

        final SQLiteDatabase dbCon = koneksiDB.getReadableDatabase();
        cursor = dbCon.rawQuery("SELECT * FROM buku WHERE judul_buku = '"+getIntent().getStringExtra("key_judul")+"'", null);
        cursor.moveToFirst();
        if (cursor.getCount() == 1) {
            cursor.moveToPosition(0);
            id = cursor.getString(0);
            et_edit_buku_judul.setText(cursor.getString(1));
            et_edit_buku_pengarang.setText(cursor.getString(2));
            et_edit_buku_tahun.setText(cursor.getString(3));
            et_edit_buku_penerbit.setText(cursor.getString(4));
        } else {
            Intent toMain = new Intent(edit_buku_activity.this, MainActivity.class);
            Toast.makeText(edit_buku_activity.this, "Terdapat Kesalahan", Toast.LENGTH_SHORT).show();
            startActivity(toMain);
        }

        btn_edit_buku = (Button)findViewById(R.id.btn_edit_buku);
        btn_edit_buku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judul = et_edit_buku_judul.getText().toString();
                pengarang = et_edit_buku_pengarang.getText().toString();
                tahun = et_edit_buku_tahun.getText().toString();
                penerbit = et_edit_buku_penerbit.getText().toString();
                if ((judul.equals("")) || (pengarang.equals("")) || (tahun.equals("")) || (penerbit.equals(""))) {
                    Toast.makeText(edit_buku_activity.this, "Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase writeDB = koneksiDB.getWritableDatabase();
                    writeDB.execSQL("update buku SET judul_buku='" + judul+"', " +"nama_pengarang='"+ pengarang +"'," +"tahun_terbit='"+ tahun +"'," +"penerbit='" + penerbit + "' WHERE id = '" + id + "'");
                    Toast.makeText(getApplicationContext(), "Update Berhasil", Toast.LENGTH_SHORT).show();
                    MainActivity.mainAct.tampilData();
                    finish();
                }

            }
        });
    }
}
