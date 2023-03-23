package com.example.weatherapplication.database;

import android.content.Context;

import com.example.weatherapplication.dao.DAo;
import com.example.weatherapplication.entité.FragTableDataset;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FragTableDataset.class}, version = 6, exportSchema = true)
public abstract class baseDeDonnes extends RoomDatabase{

    public abstract DAo MyDAo();

    private static baseDeDonnes instance;

    // creating database
    // si on a version = 2 de db alors(fallbackToDestructiveMigration) cela detruit la db precedente
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
