package com.ambrizals.wallme;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {
    dbControl dbControl;
    public static MainActivity mainAct;
    protected Cursor cursor;
    TextView tv_saldo;
    TextView tv_total_pemasukan;
    ListView lv_wallme;
    // Button show pemasukan dan show pengeluaran
    Button btn_shpmsk;
    Button btn_shplr;
    Menu menu;

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
    private HashMap<String, String> setDataPmsk(String nama_pemasukan, String jumlah_Pemasukan) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("nama_pemasukan", nama_pemasukan);
        item.put("jumlah_pemasukan", jumlah_Pemasukan);
        return item;
    }

    void tampilPemasukan() {
        // Start Bagian List View
        Cursor data = dbControl.pemasukanList();
        if(data.getCount() == 0) {
            Toast.makeText(this,"Something wrong !", Toast.LENGTH_SHORT).show();
        } else {
            // Masukkan data dari dbms ke array
            final ArrayList<Map<String, String>> listPmsk = new ArrayList<Map<String,String>>();
            Cursor dtpm = dbControl.pemasukanList();
            if (dtpm.getCount() == 0) {
                Toast.makeText(this, "Something Wrong ERR:LISTDATAPMSK !", Toast.LENGTH_SHORT).show();
            } else {
                while(dtpm.moveToNext()) {
                    listPmsk.add(setDataPmsk(dtpm.getString(1)+ " ("+dtpm.getString(0)+ ")", dtpm.getString(2)));
                }
            }
            // Masukan data yang telah disimpan ke array ke listView
            final ArrayList<Map<String, String>> listPemasukan = listPmsk;
            String[] dat = {"nama_pemasukan", "jumlah_pemasukan"};
            int[] target = {android.R.id.text1, android.R.id.text2};
            SimpleAdapter tampilPemasukan = new SimpleAdapter(this, listPemasukan, android.R.layout.simple_list_item_2,
                    dat, target);
            lv_wallme.setAdapter(tampilPemasukan);
            lv_wallme.setSelected(true);
            lv_wallme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Pengambilan ID dari nama pemasukan
                    String lp = listPmsk.get(position).get("nama_pemasukan");
                    final String id_pmsk;
                    Pattern pattern = Pattern.compile("\\d+");
                    Matcher matcher = pattern.matcher(lp);
                    if (matcher.find()) {
                        id_pmsk = matcher.group(0);
                    } else {
                        id_pmsk = "INVALID";
                    }

                    if (id_pmsk == "INVALID") {
                        Toast.makeText(MainActivity.this, "Something Wrong !", Toast.LENGTH_SHORT).show();
                        Log.d("ERROR", "INVALID_REGEX");
                    } else {
                        //Tampil menu ubah atau hapus pada item pemasukan
                        final String[] dlg_pmsk = {"Ubah", "Hapus"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setItems(dlg_pmsk, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Toast.makeText(MainActivity.this, "Check Ubah ID:" + id_pmsk, Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        Toast.makeText(MainActivity.this, "Check Hapus ID:" + id_pmsk, Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        });
                        builder.create().show();
                    }
                }
            });
        }
    }

    void tampilTotal() {
        Integer total;
        Cursor tampil = dbControl.pemasukanTotal();
        tv_total_pemasukan = (TextView)findViewById(R.id.tv_total_pemasukan);
        if(tampil.moveToFirst()) {
            total = tampil.getInt(0);
            tv_total_pemasukan.setText(String.valueOf(total));
        } else {
            Toast.makeText(MainActivity.this, "Something Wrong !", Toast.LENGTH_SHORT).show();
        }
        tampil.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Wallme - Pemasukan");

        lv_wallme = (ListView)findViewById(R.id.lv_wallme);
        dbControl = new dbControl(this);
        tampilSaldo();
        tampilPemasukan();
        tampilTotal();


        // End Bagian List View

        btn_shpmsk = (Button)findViewById(R.id.btn_pemasukan);
        btn_shplr = (Button)findViewById(R.id.btn_pengeluaran);
        btn_shpmsk.setEnabled(false);
        btn_shplr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actPengeluaranList = new Intent(MainActivity.this, PengeluaranListActivity.class);
                startActivity(actPengeluaranList);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        this.menu = menu;
        menu.add(0,1,0,"Tambah Pemasukan").setIcon(android.R.drawable.btn_plus);
        menu.add(0,2,0, "Tambah Pengeluaran").setIcon(android.R.drawable.ic_menu_rotate);
        menu.add(0,3,0, "Exit").setIcon(android.R.drawable.ic_menu_close_clear_cancel);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 1 :
                Toast.makeText(MainActivity.this, "Tambah", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
