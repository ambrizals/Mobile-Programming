package com.ambrizals.katalogbuku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView lvData;
    dbControl koneksiDB;

    public static MainActivity mainAct;
    protected Cursor cursor;
    String[] daftarBuku;
    Menu menu;

    void tampilData(){
        String querySelect = "SELECT * FROM buku";
        SQLiteDatabase conDB = koneksiDB.getReadableDatabase();
        cursor = conDB.rawQuery(querySelect, null);
        daftarBuku = new String[cursor.getCount()];

        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToFirst();
            daftarBuku[i]= cursor.getString(1).toString();

        }
        lvData.setAdapter(new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1,daftarBuku));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvData = (ListView)findViewById(R.id.lv_data);
        koneksiDB = new dbControl(this);
        tampilData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        this.menu = menu;
        menu.add(0,1,0,"Tambah").setIcon(android.R.drawable.btn_plus);
        menu.add(0,2,0, "Refresh").setIcon(android.R.drawable.ic_menu_rotate);
        menu.add(0,3,0, "Exit").setIcon(android.R.drawable.ic_menu_close_clear_cancel);

        return true;
    }
}
