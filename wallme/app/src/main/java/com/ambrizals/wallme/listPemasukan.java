package com.ambrizals.wallme;

import android.database.Cursor;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class listPemasukan {
    dbControl dbControl;
    HashMap<String, String> setData(String nama_pemasukan, String jumlah_Pemasukan) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("nama_pemasukan", nama_pemasukan);
        item.put("jumlah_pemasukan", jumlah_Pemasukan);
        return item;
    }

    ArrayList<Map<String, String>> ListData(){
        ArrayList<Map<String, String>> listPemasukan = new ArrayList<Map<String,String>>();
        Cursor dtpm = dbControl.pemasukanList();
        if (dtpm.getCount() == 0) {
            Log.d("Error", "Something wrong!");
        } else {
            while(dtpm.moveToNext()) {
                listPemasukan.add(setData(dtpm.getString(1), dtpm.getString(2)));
            }
        }
        return listPemasukan;
    }
}
