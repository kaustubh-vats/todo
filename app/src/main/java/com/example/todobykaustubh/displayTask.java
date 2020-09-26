package com.example.todobykaustubh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.todobykaustubh.data.MyDbHandler;
import com.example.todobykaustubh.model.Task;

import java.util.Calendar;

public class displayTask extends AppCompatActivity {
    EditText textView1;
    ImageButton imageButton;
    EditText textView2;
    ImageView imageView;
    Task task;
    String change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);
        Intent intent = getIntent();
        final int rid = intent.getIntExtra("idr", 0);
        String rname = intent.getStringExtra("rname");
        String rdesc = intent.getStringExtra("rdesc");
        final String ralarm = intent.getStringExtra("ralarm");
        change=ralarm;
        task = new Task(rid, rname, rdesc, ralarm);
        textView1 = findViewById(R.id.textView8);
        textView2 = findViewById(R.id.textView9);
        imageView = findViewById(R.id.imageView5);
        if (ralarm.equals("TRUE")) imageView.setImageResource(R.drawable.alarm_on);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(change.equals("TRUE"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(displayTask.this);
                    builder.setIcon(R.drawable.hook);
                    builder.setMessage("If you disable the alarm for this task. You won't be able to set it again "+task.getname()).setTitle("Do You really want to cancel the alarm");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent1=new Intent(displayTask.this,alarmSet.class);
                            intent1.putExtra("id",rid);
                            intent1.putExtra("flg",false);
                            startActivity(intent1);
                            change="FALSE";
                            imageView.setImageResource(R.drawable.alarm_off);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if(change.equals("FALSE")){
                    Toast.makeText(displayTask.this, "You can not set a alarm for this Task as you have Disabled it", Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView1.setText(rname);
        textView2.setText(rdesc);
        imageButton = findViewById(R.id.imageButton9);

    }
    public void deltask(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(displayTask.this);
        builder.setIcon(R.drawable.hook);
        builder.setMessage("Do you really want to delete this task named "+task.getname()).setTitle("Delete Task");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDbHandler db = new MyDbHandler(displayTask.this);
                db.deleteTask(task.getId());
                if(change.equals("TRUE")){
                    Calendar calendar=Calendar.getInstance();
                    Intent intent1 = new Intent(displayTask.this, alarmSet.class);
                    intent1.putExtra("id", task.getId());
                    intent1.putExtra("flg", false);
                    intent1.putExtra("day", calendar.get(Calendar.DAY_OF_MONTH));
                    intent1.putExtra("month", calendar.get(Calendar.MONTH));
                    intent1.putExtra("year", calendar.get(Calendar.YEAR));
                    intent1.putExtra("hour", calendar.get(Calendar.HOUR_OF_DAY));
                    intent1.putExtra("minute", calendar.get(Calendar.MINUTE));
                    intent1.putExtra("name",task.getname());
                    intent1.putExtra("desc",task.getDescription());
                    startActivity(intent1);
                }
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
        }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void updateThis(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(displayTask.this);
        builder.setIcon(R.drawable.hook);
        builder.setMessage("Do you really want to Update the changes in "+task.getname()).setTitle("Update Task");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Task t=new Task();
                t.setname(textView1.getText().toString());
                t.setDescription(textView2.getText().toString());
                t.setId(task.getId());
                if(change.equals("TRUE")){
                    Calendar calendar=Calendar.getInstance();
                    Intent intent1 = new Intent(displayTask.this, alarmSet.class);
                    intent1.putExtra("id", task.getId());
                    intent1.putExtra("flg", false);
                    intent1.putExtra("day", calendar.get(Calendar.DAY_OF_MONTH));
                    intent1.putExtra("month", calendar.get(Calendar.MONTH));
                    intent1.putExtra("year", calendar.get(Calendar.YEAR));
                    intent1.putExtra("hour", calendar.get(Calendar.HOUR_OF_DAY));
                    intent1.putExtra("minute", calendar.get(Calendar.MINUTE));
                    intent1.putExtra("name",task.getname());
                    intent1.putExtra("desc",task.getDescription());
                }
                t.setAlarm(change);
                MyDbHandler db=new MyDbHandler(displayTask.this);
                int i= db.updateTask(t);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
