package com.example.todobykaustubh.model;

public class Task {
    private int id;
    private String name;
    private String description;
    private String alarm;

    public Task(int id, String name, String description, String alarm) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.alarm = alarm;
    }

    public Task(String name, String description, String alarm) {
        this.name = name;
        this.description = description;
        this.alarm = alarm;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public String getname() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }
}
