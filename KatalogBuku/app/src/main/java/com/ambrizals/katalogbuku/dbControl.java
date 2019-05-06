package com.ambrizals.katalogbuku;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbControl extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "katalog_buku.db";
    public static final int DATABASE_VERSION = 1;

    public dbControl(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String sql = "create table buku (id integer primary key autoincrement, judul_buku text null, nama_pengarang text null, tahun_terbit text null, penerbit text null";
        Log.d("Data", "onCreate" + sql);
        db.execSQL(sql);
        sql = "INSERT INTO buku (id, judul_buku, nama_pengarang, tahun_terbit, penerbit) VALUES (1, 'Penerapan Sistem', 'Jogiyanto','2010','Andi')";
        Log.d("Data","bulkData" + sql);
        db.execSQL(sql);
        sql = "INSERT INTO buku (id, judul_buku, nama_pengarang, tahun_terbit, penerbit) VALUES (2, 'Pengantar TI', 'Sutarman','2012','Bumi Aksara')";
        Log.d("Data", "Bulk Data" + sql);
        db.execSQL(sql);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
