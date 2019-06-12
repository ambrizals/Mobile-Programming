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

public class ubah_pengeluaran extends AppCompatActivity {
    String id_pengeluaran;
    Integer saldo, jumlah_pengeluaran;
    EditText et_nama_pengeluaran;
    EditText et_jumlah_pengeluaran;
    EditText et_tanggal_pengeluaran;
    Button btn_ubah_pengeluaran;
    Button btn_batal;
    dbControl dbControl;
    Cursor cursor;
    Helpers helpers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Wallme - Ubah Pengeluaran");
        setContentView(R.layout.activity_ubah_pengeluaran);
        dbControl = new dbControl(this);
        helpers = new Helpers();
        id_pengeluaran = getIntent().getStringExtra("id_plr");

        // Init Variable
        et_nama_pengeluaran = (EditText)findViewById(R.id.et_ub_nama_pengeluaran);
        et_jumlah_pengeluaran = (EditText)findViewById(R.id.et_ub_jumlah_pengeluaran);
        et_tanggal_pengeluaran = (EditText)findViewById(R.id.et_tanggal_pengeluaran);
        btn_batal = (Button)findViewById(R.id.btn_batal_4);
        btn_ubah_pengeluaran = (Button)findViewById(R.id.btn_ubah_pengeluaran);

        // Panggil Data Saldo Dari SQlite
        String querySaldo = "SELECT * FROM SALDO";
        SQLiteDatabase conDB = dbControl.getReadableDatabase();
        cursor = conDB.rawQuery(querySaldo, null);
        if (cursor.moveToFirst()) {
            saldo = cursor.getInt(1);
        }

        // Panggil Data Dari SQlite
        SQLiteDatabase bacaData = dbControl.getReadableDatabase();
        cursor = bacaData.rawQuery("SELECT * FROM pengeluaran WHERE id_pengeluaran = '"+id_pengeluaran+"'", null);
        cursor.moveToFirst();
        if (cursor.getCount() == 1) {
            // Set Data
            cursor.moveToPosition(0);
            et_nama_pengeluaran.setText(cursor.getString(1));
            jumlah_pengeluaran = Integer.valueOf(cursor.getString(2));
            et_jumlah_pengeluaran.setText(cursor.getString(2));
            et_tanggal_pengeluaran.setText(helpers.dateFormat(cursor.getString(3)));
        }

        // Button Action
        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_ubah_pengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((et_nama_pengeluaran.getText().toString().equals("")) || (et_jumlah_pengeluaran.getText().toString().equals(""))){
                    Toast.makeText(ubah_pengeluaran.this, "Nama dan Jumlah Pengeluaran tidak boleh kosong !", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase tulisData = dbControl.getWritableDatabase();
                    String queryUpdate = "UPDATE pengeluaran set nama_pengeluaran = '"+et_nama_pengeluaran.getText().toString()+"', jumlah_pengeluaran ='"+et_jumlah_pengeluaran.getText().toString()+"' where id_pengeluaran ='"+id_pengeluaran+"'";
                    tulisData.execSQL(queryUpdate);

                    Integer compare = Integer.valueOf(et_jumlah_pengeluaran.getText().toString());
                    Integer selisih = compare - jumlah_pengeluaran;
                    Integer saldoAkhir;

                    saldoAkhir = saldo - selisih;
                    dbControl.updateSaldo(saldoAkhir.toString());

                    Toast.makeText(ubah_pengeluaran.this, "Data berhasil diubah !", Toast.LENGTH_SHORT).show();
                    PengeluaranListActivity.plrAct.runProperty();
                    finish();

                }
            }
        });
    }
//    @Override
//    public void onBackPressed(){
//        Intent toPengeluaran = new Intent(ubah_pengeluaran.this, PengeluaranListActivity.class);
//        startActivity(toPengeluaran);
//        finish();
//    }
}
