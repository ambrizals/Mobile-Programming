package com.ambrizals.wallme;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dbControl extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "wallme.db";
    public static final int DATABASE_VERSION = 1;

    public dbControl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS.SSS");
        Date date = new Date();
        String tanggalSekarang = dateFormat.format(date);

        String sql_saldo = "create table saldo (id_saldo integer primary key autoincrement, jumlah_saldo integer null)";
        Log.d("Data", "onCreate" + sql_saldo);
        db.execSQL(sql_saldo);
        String sql_pemasukan = "create table pemasukan (id_pemasukan integer primary key autoincrement, nama_pemasukan text null, jumlah_pemasukan integer null, created_at date null, updated_at datetime null)";
        Log.d("Data", "onCreate" + sql_pemasukan);
        db.execSQL(sql_pemasukan);
        String sql_pengeluaran = "create table pengeluaran (id_pengeluaran integer primary key autoincrement, nama_pengeluaran text null, jumlah_pengeluaran integer null, created_at datetime null, updated_at date null)";
        Log.d("Data", "onCreate" + sql_pengeluaran);
        db.execSQL(sql_pengeluaran);

        String sql_dumy_saldo = "insert into saldo (jumlah_saldo) values (0)";
        Log.d("Data", "onCreate" + sql_dumy_saldo);
        db.execSQL(sql_dumy_saldo);

//        String sql_dumy_pemasukan = "insert into pemasukan (nama_pemasukan, jumlah_pemasukan, created_at, updated_at) values ('Saldo Utama', '4000', '"+tanggalSekarang+"', '"+tanggalSekarang+"')";
//        Log.d("data", "OnCreate" + sql_dumy_pemasukan);
//        db.execSQL(sql_dumy_pemasukan);
//
//        sql_dumy_pemasukan = "insert into pemasukan (nama_pemasukan, jumlah_pemasukan, created_at, updated_at) values ('Saldo Utama', '3000', '"+tanggalSekarang+"', '"+tanggalSekarang+"')";
//        Log.d("data", "OnCreate" + sql_dumy_pemasukan);
//        db.execSQL(sql_dumy_pemasukan);
//
//        String sql_dumy_pengeluaran = "insert into pengeluaran (nama_pengeluaran, jumlah_pengeluaran, created_at, updated_at) values ('Beli 1', '1000', '"+tanggalSekarang+"', '"+tanggalSekarang+"')";
//        Log.d("data", "onCreate" + sql_dumy_pengeluaran);
//        db.execSQL(sql_dumy_pengeluaran);
//
//        sql_dumy_pengeluaran = "insert into pengeluaran (nama_pengeluaran, jumlah_pengeluaran, created_at, updated_at) values ('Beli 1', '1000', '"+tanggalSekarang+"', '"+tanggalSekarang+"')";
//        Log.d("data", "onCreate" + sql_dumy_pengeluaran);
//        db.execSQL(sql_dumy_pengeluaran);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor pemasukanList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM PEMASUKAN ORDER BY id_pemasukan DESC", null);
        return data;
    }

    public Cursor pengeluaranList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM PENGELUARAN ORDER BY id_pengeluaran DESC", null);
        return data;
    }

    public Cursor pemasukanTotal() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT SUM(jumlah_pemasukan) as jumlah_pemasukan from pemasukan", null);
        return data;
    }

    public Cursor pengeluaranTotal() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT SUM(jumlah_pengeluaran) from pengeluaran", null);
        return data;
    }

}
