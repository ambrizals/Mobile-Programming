package com.ambrizals.simpaninternalexternal;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText inputData, namaFile;
    private Button internal, external;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputData = (EditText)findViewById(R.id.inputData);
        namaFile = (EditText)findViewById(R.id.namaFile);
        internal = (Button)findViewById(R.id.btnInternal);
        external = (Button)findViewById(R.id.btnExternal);


        internal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setData = inputData.getText().toString();
                FileOutputStream fileOutputStream;
                if (namaFile.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Nama File Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        fileOutputStream = openFileOutput(namaFile.getText().toString()+".txt", Context.MODE_PRIVATE);
                        fileOutputStream.write(setData.getBytes());
                        fileOutputStream.close();
                        Toast.makeText(getApplicationContext(), "Data berhasil disimpan di internal", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        external.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    File dirExternal = Environment.getExternalStorageDirectory();
                    File createDir = new File(dirExternal.getAbsolutePath()+"/ContohDirektori");
                    if(!createDir.exists()) {
                        createDir.mkdir();
                    }
                    if (namaFile.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Nama File Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        File file = new File(createDir, namaFile.getText().toString()+".txt");
                        String setData = inputData.getText().toString();
                        FileOutputStream fileOutputStream;
                        try {
                            fileOutputStream = new FileOutputStream(file);
                            fileOutputStream.write(setData.getBytes());
                            fileOutputStream.close();
                            Toast.makeText(getApplicationContext(), "Data disimpan di external", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Penyimpanan External Tidak Tersedia", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
