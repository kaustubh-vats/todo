package com.example.todobykaustubh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import com.example.todobykaustubh.adaptor.RecycleViewAdaptor;
import com.example.todobykaustubh.data.MyDbHandler;
import com.example.todobykaustubh.model.Task;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecycleViewAdaptor recycleViewAdaptor;
    private ArrayList<Task> taskArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart(){
        super.onStart();
        MyDbHandler db = new MyDbHandler(MainActivity.this);
        taskArrayList = new ArrayList<>();
        List<Task> tasklist = db.getAllTasks();
        for(Task task: tasklist){
            taskArrayList.add(task);
        }
        recycleViewAdaptor = new RecycleViewAdaptor(MainActivity.this, taskArrayList);
        recyclerView.setAdapter(recycleViewAdaptor);
    }
    public void addtask(View view){
        final Intent intent = new Intent(MainActivity.this, addactivity.class);
        if(Build.VERSION.SDK_INT >= 21) {
            final View v = findViewById(R.id.constrained);
            final View startview = findViewById(R.id.button);
            int cx = (startview.getLeft() + startview.getRight()) / 2;
            int cy = (startview.getTop() + startview.getBottom()) / 2;
            int finalrad = Math.max(cy, v.getHeight());
            Animator animator = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalrad);
            animator.setDuration(400);
            v.setVisibility(v.VISIBLE);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
            startActivity(intent);
        }
        else
        {
            startActivity(intent);
        }
    }
    public void opensetting(View view)
    {
        final Intent intent = new Intent(this, seting.class);
        if(Build.VERSION.SDK_INT >= 21) {
            final View v = findViewById(R.id.constrained);
            final View startview = findViewById(R.id.imageButton);
            int cx = (startview.getLeft() + startview.getRight()) / 2;
            int cy = (startview.getTop() + startview.getBottom()) / 2;
            int finalrad = Math.max(cy, v.getHeight());
            Animator animator = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalrad);
            animator.setDuration(400);
            v.setVisibility(v.VISIBLE);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
            startActivity(intent);
        }
        else {
            startActivity(intent);
        }
    }
}