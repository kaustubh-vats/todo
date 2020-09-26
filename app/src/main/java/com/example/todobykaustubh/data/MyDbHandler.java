package com.example.todobykaustubh.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todobykaustubh.model.Task;
import com.example.todobykaustubh.params.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
    public MyDbHandler(Context context){
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "(" + Params.KEY_ID + " INTEGER PRIMARY KEY, " + Params.KEY_TASK_NAME + " TEXT, " + Params.KEY_TASK_DESC + " TEXT, " + Params.KEY_ALARM + " TEXT" + ")";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNewTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK_NAME,task.getname());
        values.put(Params.KEY_TASK_DESC,task.getDescription());
        values.put(Params.KEY_ALARM,task.getAlarm());
        db.insert(Params.TABLE_NAME,null,values);
        db.close();
    }

    public List<Task> getAllTasks(){
        List<Task> TaskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM "+Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do{
                Task task = new Task();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setname(cursor.getString(1));
                task.setDescription(cursor.getString(2));
                task.setAlarm(cursor.getString(3));
                TaskList.add(task);
            }while(cursor.moveToNext());
        }
        return TaskList;
    }

    public int updateTask(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK_NAME,task.getname());
        values.put(Params.KEY_TASK_DESC,task.getDescription());
        values.put(Params.KEY_ALARM,task.getAlarm());
        return db.update(Params.TABLE_NAME, values, Params.KEY_ID + " =?",new String[]{String.valueOf(task.getId())});
    }

    public void deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME, Params.KEY_ID +" =?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String query = "SELECT * FROM "+Params.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }
}
