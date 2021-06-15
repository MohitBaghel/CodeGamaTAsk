package com.example.codegamatask.Retrofit;

import com.example.codegamatask.models.byLocation.ResponsegetSearchDetails;
import com.example.codegamatask.models.searchModel.ResponsegetSearch;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("restaurants/search/geo")
    Call<ResponsegetSearchDetails> getSearchGeo (@HeaderMap Map<String,String> map,
                                                 @Query("lat") double latitiude,
                                                 @Query("lon") double longitude,
                                                 @Query("distance") int distance);

    @GET("restaurants/search/fields")
    Call<ResponsegetSearch> getSearchByFiels (@HeaderMap Map<String,String> map,
                                              @Query("restaurant_name") String name);



}
