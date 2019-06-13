package com.ambrizals.wallme;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
    public static PengeluaranListActivity plrAct;
    protected Cursor cursor;
    TextView tv_saldo;
    TextView tv_total_pengeluaran;
    ListView lv_wallme;
    Integer total_pengeluaran;
    // Button show pemasukan dan show pengeluaran
    Button btn_shpmsk;
    Button btn_shplr;
    Menu menu;
    Helpers Helpers;
    BottomNavigationView navigation;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText nama_pemasukan, jumlah_pemasukan, nama_pengeluaran, jumlah_pengeluaran;
    Activity currentActivity = PengeluaranListActivity.this;


    // Tampil jumlah saldo
    void tampilSaldo() {
        String querySaldo = "SELECT * FROM SALDO";
        SQLiteDatabase conDB = dbControl.getReadableDatabase();
        cursor = conDB.rawQuery(querySaldo, null);
        tv_saldo = (TextView) findViewById(R.id.tv_saldo);
        if (cursor.moveToFirst()) {
            String saldo = cursor.getString(1);
            String cur = Helpers.rupiah(Double.valueOf(saldo));
            tv_saldo.setText(cur);
        }
    }

    // Tampil Daftar Pengeluaran
    private HashMap<String, String> setDataPmsk(String nama_pengeluaran, String jumlah_pengeluaran) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("nama_pengeluaran", nama_pengeluaran);
        item.put("jumlah_pengeluaran", jumlah_pengeluaran);
        return item;
    }

    void tampilPengeluaran() {
        // Bagian List View
        Cursor data = dbControl.pengeluaranList();
        total_pengeluaran = 0;
        if (data.getCount() == 0) {
            TextView emptyText = (TextView) findViewById(R.id.tv_empty);
            lv_wallme.setEmptyView(emptyText);
        } else {
            // Masukkan data dari dbms ke array
            final ArrayList<Map<String, String>> listPlr = new ArrayList<Map<String, String>>();
            Cursor dtpm = dbControl.pengeluaranList();
            if (dtpm.getCount() == 0) {
                Toast.makeText(this, "Something Wrong ERR:LISTDATAPMSK !", Toast.LENGTH_SHORT).show();
            } else {
                while (dtpm.moveToNext()) {
                    total_pengeluaran = total_pengeluaran + Integer.valueOf(dtpm.getString(2));
                    listPlr.add(setDataPmsk(dtpm.getString(1) + " (" + dtpm.getString(0) + ")", Helpers.rupiah(Double.valueOf(dtpm.getString(2)))));
                }
            }
            // Masukan data yang telah disimpan ke array ke listView
            final ArrayList<Map<String, String>> listPengeluaran = listPlr;
            String[] dat = {"nama_pengeluaran", "jumlah_pengeluaran"};
            int[] target = {android.R.id.text1, android.R.id.text2};
            SimpleAdapter tampilPengeluaran = new SimpleAdapter(this, listPengeluaran, android.R.layout.simple_list_item_2,
                    dat, target);
            lv_wallme.setAdapter(tampilPengeluaran);
            tv_total_pengeluaran = (TextView) findViewById(R.id.tv_total_pengeluaran);
            tv_total_pengeluaran.setText(Helpers.rupiah(Double.valueOf(total_pengeluaran.toString())));
            lv_wallme.setSelected(true);
            lv_wallme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Pengambilan ID dari nama pengeluaran
                    String lp = listPlr.get(position).get("nama_pengeluaran");
                    final String plr = listPlr.get(position).get("jumlah_pengeluaran");
                    final String id_plr;
                    Pattern pattern = Pattern.compile("(\\d+)(?!.*\\d)");
                    Matcher matcher = pattern.matcher(lp);
                    if (matcher.find()) {
                        id_plr = matcher.group(0);
                    } else {
                        id_plr = "INVALID";
                    }

                    if (id_plr == "INVALID") {
                        Toast.makeText(currentActivity, "Something Wrong !", Toast.LENGTH_SHORT).show();
                        Log.d("ERROR", "INVALID_REGEX");
                    } else {
                        //Tampil menu ubah atau hapus pada item pengeluaran
                        final String[] dlg_pmsk = {"Ubah", "Hapus"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
                        builder.setTitle("Aksi Pengeluaran");
                        builder.setItems(dlg_pmsk, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent ubahPengeluaran = new Intent(currentActivity, ubah_pengeluaran.class);
                                        ubahPengeluaran.putExtra("id_plr", id_plr);
                                        startActivity(ubahPengeluaran);
                                        break;
                                    case 1:
                                        Integer pengeluaranData = 0;
                                        SQLiteDatabase bacaData = dbControl.getReadableDatabase();
                                        String queryItem = "SELECT * FROM PENGELUARAN WHERE id_pengeluaran = '" + id_plr + "'";
                                        cursor = bacaData.rawQuery(queryItem, null);
                                        if (cursor.moveToFirst()) {
                                            pengeluaranData = cursor.getInt(2);
                                        }

                                        String queryDel = "DELETE FROM pengeluaran where id_pengeluaran = '" + id_plr + "'";
                                        SQLiteDatabase db = dbControl.getWritableDatabase();
                                        db.execSQL(queryDel);

                                        String querySaldo = "SELECT * FROM SALDO";
                                        cursor = bacaData.rawQuery(querySaldo, null);
                                        if (cursor.moveToFirst()) {
                                            if (!(pengeluaranData == 0)) {
                                                Integer saldoAkhir;
                                                String saldo = cursor.getString(1);
                                                saldoAkhir = Integer.valueOf(saldo) + pengeluaranData;
                                                dbControl.updateSaldo(saldoAkhir.toString());
                                            } else {
                                                Toast.makeText(currentActivity, "Something Wrong !", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        Toast.makeText(PengeluaranListActivity.this, "Data Pengeluaran Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                                        Intent refreshPengeluaran = new Intent(currentActivity, PengeluaranListActivity.class);
                                        startActivity(refreshPengeluaran);
                                        finish();
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
    }

    // Jalankan properti yang telah disiapkan
    void runProperty() {
        tampilSaldo();
        tampilPengeluaran();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Wallme - Pengeluaran");
        plrAct = this;
        setContentView(R.layout.activity_pengeluaran_list_act);
        lv_wallme = (ListView) findViewById(R.id.lv_wallme);
        dbControl = new dbControl(this);
        Helpers = new Helpers();
        runProperty();
        navigation = (BottomNavigationView) findViewById(R.id.menu_bottom);
        navigation.setSelectedItemId(R.id.menu_pengeluaran);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_pemasukan:
                        Intent tambahPemasukan = new Intent(currentActivity, MainActivity.class);
                        startActivity(tambahPemasukan);
                        finish();
                        break;

                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.quick_tool,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_about:
                Intent about = new Intent(currentActivity, kelompok.class);
                startActivity(about);
                break;
            case R.id.menu_tambah_pemasukan:
                dialog = new AlertDialog.Builder(currentActivity);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.form_tambah_pemasukan, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
                dialog.setIcon(R.drawable.ic_add_circle_black);
                dialog.setTitle("Tambah Pemasukan");
                nama_pemasukan = (EditText)dialogView.findViewById(R.id.et_tambah_nama_pemasukan);
                jumlah_pemasukan = (EditText)dialogView.findViewById(R.id.et_tambah_jumlah_pemasukan);

                nama_pemasukan.setText(null);
                jumlah_pemasukan.setText(null);

                dialog.setPositiveButton("Tambah Pemasukan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ((nama_pemasukan.getText().toString().equals("")) || (jumlah_pemasukan.getText().toString().equals(""))) {
                            Toast.makeText(currentActivity, "Nama Pemasukan dan Jumlah Pemasukan Wajib di Isi !", Toast.LENGTH_SHORT).show();
                        } else {
                            dbControl.insertPemasukan(nama_pemasukan.getText().toString(), jumlah_pemasukan.getText().toString());
                            Toast.makeText(currentActivity, "Pemasukan Berhasil Ditambah", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Intent pemasukan = new Intent(currentActivity, MainActivity.class);
                            startActivity(pemasukan);
                            finish();
                        }
                    }
                });
                dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;

            case R.id.menu_tambah_pengeluaran:
                dialog = new AlertDialog.Builder(currentActivity);
                inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.form_tambah_pengeluaran, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
                dialog.setTitle("Tambah Pengeluaran");

                nama_pengeluaran = (EditText)dialogView.findViewById(R.id.et_tambah_nama_pengeluaran);
                jumlah_pengeluaran = (EditText)dialogView.findViewById(R.id.et_tambah_jumlah_pengeluaran);

                nama_pengeluaran.setText(null);
                jumlah_pengeluaran.setText(null);

                dialog.setPositiveButton("Tambah Pengeluaran", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ((nama_pengeluaran.getText().toString().equals("")) || (jumlah_pengeluaran.getText().toString().equals("")) ){
                            Toast.makeText(currentActivity, "Nama dan Jumlah Pengeluaran Wajib di Isi !", Toast.LENGTH_SHORT).show();
                        } else {
                            dbControl.insertPengeluaran(nama_pengeluaran.getText().toString(), jumlah_pengeluaran.getText().toString());
                            Toast.makeText(currentActivity, "Pengeluaran Berhasil Ditambah", Toast.LENGTH_SHORT).show();
                            Intent pengeluaran = new Intent(currentActivity, PengeluaranListActivity.class);
                            startActivity(pengeluaran);
                            finish();
                        }
                    }
                });
                dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
        return false;
    }
}
