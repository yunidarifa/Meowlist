package com.yundev.meowlist.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yundev.meowlist.model.Cat;

import java.util.List;

@Dao
public interface CatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Cat> cats);

    @Query("SELECT DISTINCT * FROM meowlist")
    LiveData<List<Cat>> getCats();

    @Query("DELETE FROM meowlist")
    void deleteAll();
}
