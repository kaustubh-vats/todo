package com.example.todobykaustubh;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todobykaustubh.data.MyDbHandler;
import com.example.todobykaustubh.model.Task;

import java.util.Calendar;

public class addactivity extends AppCompatActivity {
    Switch switc;
    Calendar calendar;
    EditText editText1;
    EditText editText2;
    boolean isTimeSelected=false;
    boolean isDateSelected=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        switc=findViewById(R.id.switch2);
        editText1=findViewById(R.id.editText);
        editText2=findViewById(R.id.editText2);
        switc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    calendar=Calendar.getInstance();
                    handleDate();
                }
            }
        });
    }


    private void handleTime(){
        boolean is24hour= DateFormat.is24HourFormat(this);
        Calendar c=Calendar.getInstance();
        TimePickerDialog timePickerDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND,0);
                isTimeSelected=true;
                Toast.makeText(addactivity.this, "date "+calendar.get(Calendar.DAY_OF_MONTH)+" Month "+calendar.get(Calendar.MONTH)+" Year "+calendar.get(Calendar.YEAR)+" Hour "+calendar.get(Calendar.HOUR_OF_DAY)+" minute "+calendar.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
            }
        },c.get(Calendar.HOUR),c.get(Calendar.MINUTE),is24hour);
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                isTimeSelected=false;
                switc.setChecked(false);
            }
        });
        timePickerDialog.setTitle("Select time");
        timePickerDialog.show();
    }
    private void handleDate(){
        Calendar c=Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                isDateSelected=true;
                handleTime();
                }
        },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                isDateSelected=false;
                switc.setChecked(false);
            }
        });
        datePickerDialog.setTitle("Choose Date");
        datePickerDialog.show();
    }

    public void addClicked(View view)
    {
        MyDbHandler db = new MyDbHandler(addactivity.this);
        Task task=new Task();
        task.setname(editText1.getText().toString());
        task.setDescription(editText2.getText().toString());

        if(switc.isChecked()) {
            Intent intent = new Intent(addactivity.this,alarmSet.class);
            intent.putExtra("day", calendar.get(Calendar.DAY_OF_MONTH));
            intent.putExtra("month", calendar.get(Calendar.MONTH));
            intent.putExtra("year", calendar.get(Calendar.YEAR));
            intent.putExtra("hour", calendar.get(Calendar.HOUR_OF_DAY));
            intent.putExtra("minute", calendar.get(Calendar.MINUTE));
            intent.putExtra("id",db.getCount()+1);
            intent.putExtra("flg",true);
            intent.putExtra("name",task.getname());
            intent.putExtra("desc",task.getDescription());
            startActivity(intent);
            task.setAlarm("TRUE");
        }
        else
            task.setAlarm("FALSE");
        db.addNewTask(task);
        Toast.makeText(this, "Saved Successfully", Toast.LENGTH_LONG).show();
        finish();
    }
}
