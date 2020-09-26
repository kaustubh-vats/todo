package com.example.todobykaustubh.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todobykaustubh.R;
import com.example.todobykaustubh.displayTask;
import com.example.todobykaustubh.model.Task;

import java.util.List;

public class RecycleViewAdaptor extends RecyclerView.Adapter<RecycleViewAdaptor.ViewHolder> {
    private Context context;
    private List<Task> taskList;

    public RecycleViewAdaptor(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public RecycleViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdaptor.ViewHolder holder, int position) {
        Animation animation=AnimationUtils.loadAnimation(context,R.anim.fade_transition_animation);
        animation.setDuration(500);
        holder.cardView.setAnimation(animation);
        Task task = taskList.get(position);
        holder.Taskname.setText(task.getname());
        holder.TaskDesc.setText(task.getDescription());
        String h=task.getAlarm();
        if(h.equals("TRUE")) holder.Notif.setImageResource(R.drawable.alarm_on);
        else if(h.equals("FALSE")) holder.Notif.setImageResource(R.drawable.alarm_off);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView Taskname;
        public TextView TaskDesc;
        public ImageView Notif;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cardView = itemView.findViewById(R.id.cardView);
            Taskname = itemView.findViewById(R.id.textView3);
            TaskDesc = itemView.findViewById(R.id.textView);
            Notif = itemView.findViewById(R.id.imageView2);
        }

        @Override
        public void onClick(View v){
            int position = this.getAdapterPosition();
            Task task=taskList.get(position);
            int id=task.getId();
            String taskname=task.getname();
            String taskdesc=task.getDescription();
            String alarm=task.getAlarm();
            Intent intent = new Intent(context, displayTask.class);
            intent.putExtra("idr", id);
            intent.putExtra("rname",taskname);
            intent.putExtra("rdesc",taskdesc);
            intent.putExtra("ralarm",alarm);
            context.startActivity(intent);
        }
    }
}
