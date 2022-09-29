package com.yundev.meowlist.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.yundev.meowlist.R;
import com.yundev.meowlist.adapter.CatAdapter;
import com.yundev.meowlist.api.ApiConfig;
import com.yundev.meowlist.api.ApiService;
import com.yundev.meowlist.database.Repository;
import com.yundev.meowlist.model.Cat;
import com.yundev.meowlist.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MainViewModel mainViewModel;
    private List<Cat> getCats;
    private CatAdapter catAdapter;
    private RecyclerView recyclerView;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new Repository(getApplication());
        getCats = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_cat);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        catAdapter = new CatAdapter(this, getCats, new CatAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Cat data) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("intent_img", data.getUrl());
                startActivity(intent);
            }
        });
        makeRequest();
        mainViewModel.getAllCats().observe(this, new Observer<List<Cat>>() {
            @Override
            public void onChanged(List<Cat> cats) {
                recyclerView.setAdapter(catAdapter);
                catAdapter.getAllDatas(cats);
                Log.d(TAG, "onChanged: " +cats);
            }
        });

    }

    private void makeRequest() {
        Call<List<Cat>> client = ApiConfig.getApiService().getImg(10);
        client.enqueue(new Callback<List<Cat>>() {
            @Override
            public void onResponse(Call<List<Cat>> call, Response<List<Cat>> response) {
                if (response.isSuccessful()) {
                    repository.insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Cat>> call, Throwable t) {
                Log.d(TAG, "onFailure: " +t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        return true;

    }
}