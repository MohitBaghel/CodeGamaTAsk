package com.example.codegamatask.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.codegamatask.Adapter.MenuSectionAdapter;
import com.example.codegamatask.Interface.ItemSelected;
import com.example.codegamatask.R;
import com.example.codegamatask.Retrofit.APIClient;
import com.example.codegamatask.Retrofit.ApiInterface;
import com.example.codegamatask.databinding.ActivityResturantDetailsBinding;
import com.example.codegamatask.models.resturantdetails.MenuSectionsItem;
import com.example.codegamatask.models.resturantdetails.ResponsegetResturantDetails;
import com.example.codegamatask.models.resturantdetails.Result;
import com.example.codegamatask.utils.Globals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResturantDetails extends AppCompatActivity {
    private static final String TAG = "Resturant";
    private ActivityResturantDetailsBinding binding;
    private Long id;
    private MenuSectionAdapter adapter;
    private List<MenuSectionsItem> menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_resturant_details);
        try {
            Bundle bundle = getIntent().getExtras();
            id = bundle.getLong("restId");
            Log.d(TAG,id.toString());
            menu=new ArrayList<>();
            adapter = new MenuSectionAdapter(this,menu);
            binding.menuRv.setLayoutManager(new GridLayoutManager(this,1));
            binding.menuRv.setAdapter(adapter);
            FetchDetils(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        private void FetchDetils(Long id) {
            binding.Progress.setVisibility(View.VISIBLE);
            ApiInterface api = APIClient.getClient().create(ApiInterface.class);
            HashMap<String, String> map = new HashMap<>();
            map.put("x-api-key", Globals.Key);
            Call<ResponsegetResturantDetails> call = api.getResturantDetails(id,map);
            call.enqueue(new Callback<ResponsegetResturantDetails>() {
                @Override
                public void onResponse(Call<ResponsegetResturantDetails> call, Response<ResponsegetResturantDetails> response) {
                    try {
                        binding.Progress.setVisibility(View.GONE);
                        if (response.code() == 200) {
                            setData(response.body().getResult());
                        }else{
                            Toast.makeText(ResturantDetails.this, "NO Data Found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponsegetResturantDetails> call, Throwable t) {
                    t.printStackTrace();
                    binding.Progress.setVisibility(View.GONE);

                }
            });

        }
    private void setData(Result result) {
        try {
            menu = result.getMenus().get(0).getMenuSections();
            adapter.setData(menu);

            binding.restNameTv.setText(result.getRestaurantName());
            binding.address.setText("Address: " + result.getAddress().getState() + "," + result.getAddress().getCity() + "\n" + result.getAddress().getStreet() + "," + result.getAddress().getPostalCode());
            binding.phone.setText(String.valueOf("ph: "+result.getRestaurantPhone()));
            binding.price.setText("Prices"+String.valueOf(result.getPriceRange()));
            binding.priceNum.setText(String.valueOf(result.getPriceRangeNum()));
            binding.timimg.setText("Timing: "+String.valueOf(result.getHours()));
            if (result.getRestaurantWebsite().equalsIgnoreCase(" ")) {
                binding.website.setText("Don't have Website");
            } else {
                binding.website.setText("Url: "+result.getRestaurantWebsite());
            }
            binding.cuisin.setText("Cuisines: "+result.getCuisines().toString());
            adapter.update(menu);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}