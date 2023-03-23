package com.example.weatherapplication.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.weatherapplication.dao.DAo;
import com.example.weatherapplication.database.baseDeDonnes;
import com.example.weatherapplication.entité.FragTableDataset;

import java.nio.channels.AsynchronousByteChannel;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import androidx.lifecycle.LiveData;

public class fragRepository {

    private List<FragTableDataset> mylist;
    private DAo mydao;

    public fragRepository(Application app) {
        // ici on creat database for real
        baseDeDonnes db = baseDeDonnes.getInstance(app);
        mydao = db.MyDAo();
        //mylist = mydao.getallfrag(date);
    }

    // recuperer
    public List<FragTableDataset> getMylist(String date) {
        List<FragTableDataset> mylist = mydao.getallfrag(date);

        if(mylist == null) {
            mylist = mydao.getallfrag(date);
        }
        return mylist;
    }

    public FragTableDataset getMyFrag(int id){
        return mydao.getallfragofid(id);
    }

    public void deletefrag(FragTableDataset frag){
        new asynchronDelete(mydao).execute(frag);
    }

    private class asynchronDelete extends AsyncTask<FragTableDataset, Void, Void> {

        DAo mydao;

        public asynchronDelete(DAo mydao) {
            this.mydao = mydao;
        }

        @Override
        protected Void doInBackground(FragTableDataset... fragTableDatasets) {
            this.mydao.supprimerFrag(fragTableDatasets[0]);
            return null;
        }
    }

    //----------------------------------------------

    // ajouter un object a database
    public void ajouterobject(FragTableDataset objet){
        //cela block l'app
        //donc en utilise thread de background
        new asynchronAdd(mydao).execute(objet);
    }

    private class asynchronAdd extends AsyncTask<FragTableDataset, Void, Void> {

        DAo mydao;

        public asynchronAdd(DAo mydao) {
            this.mydao = mydao;
        }

        @Override
        protected Void doInBackground(FragTableDataset... fragTableDatasets) {
            this.mydao.ajouterFrag(fragTableDatasets[0]);
            return null;
        }
    }

    //---------------------------------------------

    public void updateobject(String nameUp, String timeUp, String friendsUp, int idup){
        updateParams params = new updateParams(nameUp, timeUp, friendsUp, idup);
        new asynchronUpdate(mydao).execute(params);
    }

    private static class updateParams{
        String name, time, friends;
        int id;

        public updateParams(String name, String time, String friends, int id) {
            this.name = name;
            this.time = time;
            this.friends = friends;
            this.id = id;
        }
    }

    private class asynchronUpdate extends AsyncTask<updateParams, Void, Void>{

        DAo mydao;

        public asynchronUpdate(DAo mydao) {
            this.mydao = mydao;
        }

        @Override
        protected Void doInBackground(updateParams... param) {
            this.mydao.updateFrag(param[0].name, param[0].time, param[0].friends, param[0].id);
            return null;
        }
    }

}
