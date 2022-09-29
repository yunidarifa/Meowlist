package com.yundev.meowlist.api;

import com.yundev.meowlist.model.Cat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search")
    Call<List<Cat>> getImg(@Query("limit") Integer limit);
}
