package com.example.codegamatask.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.codegamatask.Adapter.ResturantAdapter;
import com.example.codegamatask.Interface.MVPView;
import com.example.codegamatask.R;
import com.example.codegamatask.Retrofit.APIClient;
import com.example.codegamatask.Retrofit.ApiInterface;
import com.example.codegamatask.databinding.ActivityHomeBinding;
import com.example.codegamatask.models.byLocation.DataItem;
import com.example.codegamatask.models.byLocation.ResponsegetSearchDetails;
import com.example.codegamatask.models.searchModel.ResponsegetSearch;
import com.example.codegamatask.utils.BasicUtilities;
import com.example.codegamatask.utils.Globals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity implements MVPView {


    private static final String TAG = "Home";
    private ActivityHomeBinding binding;
    private ResturantAdapter adapter;
    private List<DataItem> dataItems=new ArrayList<>();
    private Double lat=40.68919,lon=-73.992378;
    private int distance=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);
        try{
            adapter =new ResturantAdapter(this,dataItems,Home.this);
            binding.resturantRv.setLayoutManager(new GridLayoutManager(this,1));
            binding.searchEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (BasicUtilities.checkNetworkConnection(Home.this)) {
                        try {
                            if (s.toString().length() > 0) {
                                binding.resturantRv.setVisibility(View.GONE);
                                getSearchItems(s.toString());
                            } else {
//                            search_content_lo.setVisibility(View.GONE);
//                            tools_rv.setVisibility(View.VISIBLE);
//                                getCategoryList();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(Home.this, R.string.Alert_Internet, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if(BasicUtilities.checkNetworkConnection(this)) {
                nearResturant(lat, lon, distance);
            }else{
                Toast.makeText(this, R.string.Alert_Internet, Toast.LENGTH_SHORT).show();
            }

        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public void getSearchItems(String search_query) {
        try {
            binding.searchPg.setVisibility(View.VISIBLE);
            ApiInterface cr = APIClient.getClient().create(ApiInterface.class);
            Map<String, String> map = new HashMap<>();
            map.put("x-api-key", Globals.Key);
            Call<ResponsegetSearch> call = cr.getSearchByFiels(map,search_query);
            call.enqueue(new Callback<ResponsegetSearch>() {
                @Override
                public void onResponse(Call<ResponsegetSearch> call, Response<ResponsegetSearch> response) {
                    try {
//                        search_progress_bar.setVisibility(View.GONE);
                        if (response.body().getData().size() > 0 && response.code() == 200) {
                            Log.d(TAG,response.body().toString());
//                            binding.resturantRv.setVisibility(View.VISIBLE);

                        } else {
//                            no_items_found.setVisibility(View.VISIBLE);
//                            search_rv.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponsegetSearch> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
//            progress_bar.setVisibility(View.GONE);
        }
    }

    private void nearResturant(Double lat, Double lon, int distance) {
        binding.progressBar.setVisibility(View.GONE);
        ApiInterface api= APIClient.getClient().create(ApiInterface.class);
        HashMap<String,String> map=new HashMap<>();
        map.put("x-api-key", Globals.Key);
        Call<ResponsegetSearchDetails> call=api.getSearchGeo(map,lat,lon,distance);
        call.enqueue(new Callback<ResponsegetSearchDetails>() {
            @Override
            public void onResponse(Call<ResponsegetSearchDetails> call, Response<ResponsegetSearchDetails> response) {
                try{
                    binding.progressBar.setVisibility(View.GONE);
                    Log.d(TAG,response.body().toString());
                    if(response.body().getData().size() > 0 && response.code() == 200){
                        binding.resturantRv.setVisibility(View.VISIBLE);
                        dataItems=response.body().getData();
                        adapter.setData(dataItems);
                        binding.resturantRv.setAdapter(adapter);
                        adapter.update(dataItems);
                    }else{
                        Toast.makeText(Home.this, R.string.resturant_nf, Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                
               
            }

            @Override
            public void onFailure(Call<ResponsegetSearchDetails> call, Throwable t) {
                    t.printStackTrace();
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onClickPosition(int position, String name) {

    }
}