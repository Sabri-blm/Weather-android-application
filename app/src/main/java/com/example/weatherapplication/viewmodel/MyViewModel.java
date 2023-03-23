package com.example.weatherapplication.viewmodel;

import android.app.Application;

import com.example.weatherapplication.entité.FragTableDataset;
import com.example.weatherapplication.repository.fragRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MyViewModel extends AndroidViewModel {

    fragRepository repo;
    MutableLiveData<String> Name = new MutableLiveData<String>("");
    MutableLiveData<String> friends = new MutableLiveData<String>("");
    MutableLiveData<Float> time = new MutableLiveData<Float>((float) 0);

    //MutableLiveData<String> date ;

    public MyViewModel(Application application) {
        super(application);
        repo = new fragRepository(application);
    }



    public String getName() {
        return Name.getValue();
    }

    public String getFriends() {
        return friends.getValue();
    }

    public float getTime() {
        return time.getValue();
    }

    public void setName(String name) {
        this.Name.setValue(name);
    }

    public void setFriends(String friends) {
        this.friends.setValue(friends);
    }

    public void setTime(float time) {
        this.time.setValue(time);
    }

    // for the repo (code down there)

    public List<FragTableDataset> getalldata(String date){
        return repo.getMylist(date);
    }

    public FragTableDataset getFrag(int id){
        return repo.getMyFrag(id);
    }

    public void creedata(FragTableDataset data){
        repo.ajouterobject(data);
    }

    public void updatedata(String nameUp, String timeUp, String friendsUp, int idup){
        repo.updateobject(nameUp,timeUp,friendsUp,idup);
    }

    public void deletingfrag(FragTableDataset frag){ repo.deletefrag(frag);}


}
