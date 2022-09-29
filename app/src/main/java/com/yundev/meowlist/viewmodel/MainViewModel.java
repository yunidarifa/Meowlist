package com.yundev.meowlist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yundev.meowlist.database.Repository;
import com.yundev.meowlist.model.Cat;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;
    public LiveData<List<Cat>> getAllCats;

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        getAllCats = repository.getAllCats;
    }

    public void insert(List<Cat> cats) {
        repository.insert(cats);
    }

    public LiveData<List<Cat>> getAllCats() {
        return getAllCats;
    }
}
