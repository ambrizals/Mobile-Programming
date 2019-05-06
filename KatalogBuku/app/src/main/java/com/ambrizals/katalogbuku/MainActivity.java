package com.ambrizals.katalogbuku;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.MenuItem;
import android.widget.Toast;

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
            cursor.moveToPosition(i);
            daftarBuku[i]= cursor.getString(1);

        }
        lvData.setAdapter(new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1,daftarBuku));
        lvData.setSelected(true);
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection = daftarBuku[position];
                final String[] dialogItem = {"Edit", "Hapus"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan ?");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent toEdit = new Intent(MainActivity.this, edit_buku_activity.class);
                                toEdit.putExtra("key_judul", selection);
                                startActivity(toEdit);
                                break;
                            case 1:
                                String queryDel = "DELETE FROM buku WHERE judul_buku='"+selection+"'";
                                SQLiteDatabase db = koneksiDB.getWritableDatabase();
                                db.execSQL(queryDel);
                                Toast.makeText(MainActivity.this,"Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                tampilData();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvData = (ListView)findViewById(R.id.lv_data);
        mainAct = this;
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

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 1 :
                Intent toAdd = new Intent(MainActivity.this, add_buku.class);
                startActivity(toAdd);
        }
        return false;
    }
}
