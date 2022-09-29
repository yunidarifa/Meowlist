package com.yundev.meowlist.database;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.yundev.meowlist.dao.CatDao;
import com.yundev.meowlist.model.Cat;

@Database(entities = {Cat.class}, version = 1)
public abstract class CatDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Cat";
    public abstract CatDao catDao();
    public static volatile CatDatabase INSTANCE = null;

    public static CatDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (CatDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, CatDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsyn(INSTANCE);
        }
    };

    private static class PopulateDbAsyn extends AsyncTask<Void, Void, Void> {

        private CatDao catDao;

        public PopulateDbAsyn(CatDatabase catDatabase) {
            catDao = catDatabase.catDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            catDao.deleteAll();
            return null;
        }
    }
}
