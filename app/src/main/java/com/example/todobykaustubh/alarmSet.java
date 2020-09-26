package com.example.todobykaustubh;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.widget.TextView;
import java.util.Calendar;

public class alarmSet extends AppCompatActivity {
    Calendar calendar;
    TextView textView;
    String name,desc;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_set);
        Calendar c=Calendar.getInstance();
        calendar=Calendar.getInstance();
        textView=findViewById(R.id.textView10);
        Intent intent=getIntent();
        boolean flg=intent.getBooleanExtra("flg",false);
        int year=intent.getIntExtra("year",c.get(Calendar.YEAR));
        int month=intent.getIntExtra("month",c.get(Calendar.MONTH));
        int day=intent.getIntExtra("day",c.get(Calendar.DAY_OF_MONTH));
        int hour=intent.getIntExtra("hour",c.get(Calendar.HOUR_OF_DAY));
        int minutes=intent.getIntExtra("minute",c.get(Calendar.MINUTE));
        id=intent.getIntExtra("id",0);
        name = intent.getStringExtra("name");
        desc = intent.getStringExtra("desc");
        calendar.set(year,month,day,hour,minutes,0);
        textView.setText(calendar.getTime().toString());
        Intent intent1 = new Intent(alarmSet.this, AlertReciever.class);
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        intent1.putExtra("name",name);
        intent1.putExtra("desc",desc);
        intent1.putExtra("id",id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent1.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent1.setData(Uri.parse("package:" + packageName));
            }
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(alarmSet.this, id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        if(flg){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
                textView.setText("date "+calendar.get(Calendar.DAY_OF_MONTH)+" Month "+calendar.get(Calendar.MONTH)+" Year "+calendar.get(Calendar.YEAR)+" Hour "+calendar.get(Calendar.HOUR_OF_DAY)+" minute "+calendar.get(Calendar.MINUTE));
                textView.setText("Alarm Schedule "+id);
                finish();
            }
            else{
                textView.setText("DEVICE INCOMPATIBLE");
            }
        }
        else{
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            textView.setText("Cancelled alarm "+id);
            finish();
        }

    }
    /*public void setAlarm(){

    }
    public void cancelALarm(){
        Intent intent = new Intent(alarmSet.this, AlertReciever.class);
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(alarmSet.this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        textView.setText("Cancelled alarm "+id);
        finish();
    }*/
}
