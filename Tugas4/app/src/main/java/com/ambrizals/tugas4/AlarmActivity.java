package com.ambrizals.tugas4;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    TimePicker myTimePicker;
    Button buttonStartDialog;
    TextView textAlarmPrompt;
    TimePickerDialog timePickerDialog;
    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        textAlarmPrompt = (TextView)findViewById(R.id.tv_title);
        buttonStartDialog = (Button)findViewById(R.id.startSetDialog);
        buttonStartDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePickerDialog(false);
            }
        });
    }

    private void openTimePickerDialog(boolean is24r){
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(AlarmActivity.this, onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.setTitle("Set Alarm Title");
        timePickerDialog.show();
    }
    TimePickerDialog.OnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(){
            Calendar calNow = Calendar.getInstance();
            Calendar calSet = Calendar.getInstance();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0) {
                calSet.add(Calendar.DATE, 1);
                Log.i("Hasil", "=<0");
            } else if(calSet.compareTo(calNow) > 0) {
                Log.i("Hasil", "> 0");
            } else {
                Log.i("Hasil ", "Else");
            }

            setAlarm(calSet);
        }
    }

    private void setAlarm(Calendar targetCal){
        textAlarmPrompt.setText("****\n" + "Alarm Set On" + targetCal.getTime() + "\n****");
        Intent intent = new Intent(getBaseContext(), AlarmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),pendingIntent);
    }
}
