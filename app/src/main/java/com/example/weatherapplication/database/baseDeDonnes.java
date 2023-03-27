package com.example.weatherapplication.database;

import android.content.Context;

import com.example.weatherapplication.dao.DAo;
import com.example.weatherapplication.entité.FragTableDataset;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FragTableDataset.class}, version = 7, exportSchema = true)
public abstract class baseDeDonnes extends RoomDatabase{

    public abstract DAo MyDAo();

    private static baseDeDonnes instance;

    // creating database
    // if we have version = 2 in db so(fallbackToDestructiveMigration) this destroy the old version of the database and create a new one
    public static baseDeDonnes getInstance(Context context){
        if(instance == null){
            synchronized (baseDeDonnes.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context, baseDeDonnes.class, "db").fallbackToDestructiveMigration().allowMainThreadQueries().build();
                }
            }

        }
        return instance;
    }
}
