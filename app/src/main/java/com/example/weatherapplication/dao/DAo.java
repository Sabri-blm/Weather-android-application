package com.example.weatherapplication.dao;

import com.example.weatherapplication.entité.FragTableDataset;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DAo {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void ajouterFrag(FragTableDataset frag);

    @Delete
    public void supprimerFrag(FragTableDataset frag);

    @Query("SELECT * FROM FragTableDataset WHERE date=:dateup")
    public List<FragTableDataset> getallfrag(String dateup);

    @Query("SELECT * FROM FragTableDataset Where id=:ids")
    public FragTableDataset getallfragofid(int ids);

    @Query("UPDATE FragTableDataset SET name=:nameUp, hour=:timeUp, Friends=:friendsUp  WHERE id = :idup")
    void updateFrag(String nameUp, String timeUp, String friendsUp, int idup);
}
