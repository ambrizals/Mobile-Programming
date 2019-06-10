package com.ambrizals.wallme;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    dbControl dbControl;
    public static MainActivity mainAct;
    protected Cursor cursor;
    TextView tv_saldo;
    String[] daftarPemasukan;
    ListView lv_wallme;
    ArrayList<String> ListData;

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
    private HashMap<String, String> setData(String nama_pemasukan, String jumlah_Pemasukan) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("nama_pemasukan", nama_pemasukan);
        item.put("jumlah_pemasukan", jumlah_Pemasukan);
        return item;
    }

    private ArrayList<Map<String, String>> ListData(){
        ArrayList<Map<String, String>> listPemasukan = new ArrayList<Map<String,String>>();
        Cursor dtpm = dbControl.pemasukanList();
        if (dtpm.getCount() == 0) {
            //Code here
        } else {
            while(dtpm.moveToNext()) {
                listPemasukan.add(setData(dtpm.getString(1), dtpm.getString(2)));
            }
        }
        return listPemasukan;
    }

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
            ArrayList<Map<String, String>> listPemasukan = ListData();
            String[] dat = {"nama_pemasukan", "jumlah_pemasukan"};
            int[] target = {android.R.id.text1, android.R.id.text2};
            SimpleAdapter adapter = new SimpleAdapter(this, listPemasukan, android.R.layout.simple_list_item_2,
                    dat, target);
            lv_wallme.setAdapter(adapter);
        }

    }


}
