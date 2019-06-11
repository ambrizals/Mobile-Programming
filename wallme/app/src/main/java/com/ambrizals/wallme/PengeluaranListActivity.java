package com.ambrizals.wallme;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PengeluaranListActivity extends AppCompatActivity {
    dbControl dbControl;
    public static PengeluaranListActivity pengeluaranListAct;
    protected Cursor cursor;
    TextView tv_saldo;
    ListView lv_wallme;
    // Button show pemasukan dan show pengeluaran
    Button btn_shpmsk;
    Button btn_shplr;

    // Tampil jumlah saldo
    void tampilSaldo() {
        String querySaldo = "SELECT * FROM SALDO";
        SQLiteDatabase conDB = dbControl.getReadableDatabase();
        cursor = conDB.rawQuery(querySaldo, null);
        tv_saldo = (TextView)findViewById(R.id.tv_saldo);
        if (cursor.moveToFirst()) {
            String saldo = cursor.getString(1);
            tv_saldo.setText(saldo);
        }
    }

    // Tampil Daftar Pemasukan
    private HashMap<String, String> setDataPmsk(String nama_pengeluaran, String jumlah_pengeluaran) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("nama_pengeluaran", nama_pengeluaran);
        item.put("jumlah_pengeluaran", jumlah_pengeluaran);
        return item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran_list_act);
        lv_wallme = (ListView)findViewById(R.id.lv_pengeluaran);
        dbControl = new dbControl(this);
        tampilSaldo();

        // Bagian List View
        Cursor data = dbControl.pengeluaranList();
        if(data.getCount() == 0) {
            Toast.makeText(this,"Something wrong !", Toast.LENGTH_SHORT).show();
        } else {
            // Masukkan data dari dbms ke array
            final ArrayList<Map<String, String>> listPlr = new ArrayList<Map<String,String>>();
            Cursor dtpm = dbControl.pengeluaranList();
            if (dtpm.getCount() == 0) {
                Toast.makeText(this, "Something Wrong ERR:LISTDATAPMSK !", Toast.LENGTH_SHORT).show();
            } else {
                while(dtpm.moveToNext()) {
                    listPlr.add(setDataPmsk(dtpm.getString(1)+ " ("+dtpm.getString(0)+ ")", dtpm.getString(2)));
                }
            }
            // Masukan data yang telah disimpan ke array ke listView
            final ArrayList<Map<String, String>> listPengeluaran = listPlr;
            String[] dat = {"nama_pengeluaran", "jumlah_pengeluaran"};
            int[] target = {android.R.id.text1, android.R.id.text2};
            SimpleAdapter tampilPemasukan = new SimpleAdapter(this, listPengeluaran, android.R.layout.simple_list_item_2,
                    dat, target);
            lv_wallme.setAdapter(tampilPemasukan);
            lv_wallme.setSelected(true);
            lv_wallme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Pengambilan ID dari nama pemasukan
                    String lp = listPlr.get(position).get("nama_pemasukan");
                    final String id_plr;
                    Pattern pattern = Pattern.compile("\\d+");
                    Matcher matcher = pattern.matcher(lp);
                    if (matcher.find()) {
                        id_plr = matcher.group(0);
                    } else {
                        id_plr = "INVALID";
                    }

                    if (id_plr == "INVALID") {
                        Toast.makeText(PengeluaranListActivity.this, "Something Wrong !", Toast.LENGTH_SHORT).show();
                        Log.d("ERROR", "INVALID_REGEX");
                    } else {
                        //Tampil menu ubah atau hapus pada item pemasukan
                        final String[] dlg_pmsk = {"Ubah", "Hapus"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(PengeluaranListActivity.this);
                        builder.setItems(dlg_pmsk, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Toast.makeText(PengeluaranListActivity.this, "Check Ubah ID:" + id_plr, Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        Toast.makeText(PengeluaranListActivity.this, "Check Hapus ID:" + id_plr, Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        });
                        builder.create().show();
                    }
                }
            });
        }

        // End Bagian List View

        btn_shpmsk = (Button)findViewById(R.id.btn_pemasukan);
        btn_shplr = (Button)findViewById(R.id.btn_pengeluaran);
        btn_shplr.setEnabled(false);
        btn_shpmsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(PengeluaranListActivity.this, MainActivity.class);
                startActivity(toMain);
                finish();
            }
        });
    }
}
