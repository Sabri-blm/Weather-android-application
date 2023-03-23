package com.example.weatherapplication.entité;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FragTableDataset {

    @PrimaryKey(autoGenerate = true)
    int id;

    String name;
    String hour;
    String Friends;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHour() {
        return hour;
    }

    public String getFriends() {
        return Friends;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setFriends(String friends) {
        Friends = friends;
    }

    public FragTableDataset(String name, String hour, String friends, String date) {
        this.name = name;
        this.hour = hour;
        this.Friends = friends;
        this.date = date;
    }

    public FragTableDataset() {
    }
}
