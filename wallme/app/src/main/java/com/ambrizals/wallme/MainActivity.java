package com.ambrizals.wallme;

import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    dbControl dbControl;
    public static MainActivity mainAct;
    protected Cursor cursor;
    TextView tv_saldo;
    ListView lv_wallme;
    // Button show pemasukan dan show pengeluaran
    Button btn_shpmsk;
    Button btn_shplr;

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

    private ArrayList<Map<String, String>> ListDataPmsk(){
        ArrayList<Map<String, String>> listPemasukan = new ArrayList<Map<String,String>>();
        Cursor dtpm = dbControl.pemasukanList();
        if (dtpm.getCount() == 0) {
            Toast.makeText(this, "Something Wrong ERR:LISTDATAPMSK !", Toast.LENGTH_SHORT).show();
        } else {
            while(dtpm.moveToNext()) {
                listPemasukan.add(setDataPmsk(dtpm.getString(1), dtpm.getString(2)));
            }
        }
        return listPemasukan;
    }

    SimpleAdapter tampilPemasukan() {
        ArrayList<Map<String, String>> listPemasukan = ListDataPmsk();
        String[] dat = {"nama_pemasukan", "jumlah_pemasukan"};
        int[] target = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(this, listPemasukan, android.R.layout.simple_list_item_2,
                dat, target);
        return adapter;
    }

    // End Tampil Daftar Pemasukan

    // Tampil Daftar Pemasukan
    private HashMap<String, String> setDataPlr(String nama_pengeluaran, String jumlah_pengeluaran) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("nama_pengeluaran", nama_pengeluaran);
        item.put("jumlah_pengeluaran", jumlah_pengeluaran);
        return item;
    }

    private ArrayList<Map<String, String>> ListDataPlr(){
        ArrayList<Map<String, String>> listPengeluaran = new ArrayList<Map<String,String>>();
        Cursor dtpm = dbControl.pengeluaranList();
        if (dtpm.getCount() == 0) {
            Toast.makeText(this, "Something Wrong ERR:LISTDATAPLR !", Toast.LENGTH_SHORT).show();
        } else {
            while(dtpm.moveToNext()) {
                listPengeluaran.add(setDataPlr(dtpm.getString(1), dtpm.getString(2)));
            }
        }
        return listPengeluaran;
    }

    SimpleAdapter tampilPengeluaran() {
        ArrayList<Map<String, String>> listPengeluaran = ListDataPlr();
        String[] dat = {"nama_pengeluaran", "jumlah_pengeluaran"};
        int[] target = {android.R.id.text1, android.R.id.text2};
        SimpleAdapter adapter = new SimpleAdapter(this, listPengeluaran, android.R.layout.simple_list_item_2,
                dat, target);
        return adapter;
    }

    // End Tampil Daftar Pemasukan


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_wallme = (ListView)findViewById(R.id.lv_wallme);
        dbControl = new dbControl(this);
        tampilSaldo();

        ArrayList<String> listWM = new ArrayList<>();
        Cursor data = dbControl.pemasukanList();
        if(data.getCount() == 0) {
            Toast.makeText(this,"Something wrong !", Toast.LENGTH_SHORT).show();
        } else {
            lv_wallme.setAdapter(tampilPemasukan());
        }

        btn_shpmsk = (Button)findViewById(R.id.btn_pemasukan);
        btn_shplr = (Button)findViewById(R.id.btn_pengeluaran);

        btn_shplr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_wallme.setAdapter(tampilPemasukan());
            }
        });

        btn_shplr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_wallme.setAdapter(tampilPengeluaran());
            }
        });

    }


}
