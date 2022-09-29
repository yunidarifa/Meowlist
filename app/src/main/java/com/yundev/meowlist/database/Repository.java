package com.yundev.meowlist.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.yundev.meowlist.dao.CatDao;
import com.yundev.meowlist.model.Cat;

import java.util.List;

public class Repository {

    public CatDao catDao;
    public LiveData<List<Cat>> getAllCats;
    private CatDatabase database;

    public Repository(Application application) {
        database = CatDatabase.getINSTANCE(application);
        catDao = database.catDao();
        getAllCats = catDao.getCats();
    }

    public void insert(List<Cat> cats) {
        new InsertAsyncTask(catDao).execute(cats);
    }

    public LiveData<List<Cat>> getGetAllCats() {
        return getAllCats;
    }

    private static class InsertAsyncTask extends AsyncTask<List<Cat>, Void, Void> {

        private CatDao catDao;

        public InsertAsyncTask(CatDao catDao) {
            this.catDao = catDao;
        }

        @Override
        protected Void doInBackground(List<Cat>... lists) {
            catDao.insert(lists[0]);
            return null;
        }
    }
}
