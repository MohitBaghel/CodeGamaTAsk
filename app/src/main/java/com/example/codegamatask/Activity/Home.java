package com.example.codegamatask.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.codegamatask.Adapter.ResturantAdapter;
import com.example.codegamatask.Adapter.SearchAdapter;
import com.example.codegamatask.Interface.ItemSelected;
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

public class Home extends AppCompatActivity implements MVPView, LocationListener, ItemSelected {


    final String TAG = "GPS";
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    private static final String TAGG = "Home";
    private ActivityHomeBinding binding;
    private ResturantAdapter adapter;
    private SearchAdapter searchadapter;
    private List<DataItem> dataItems=new ArrayList<>();
    private List<com.example.codegamatask.models.searchModel.DataItem> data=new ArrayList<>();
    private Double lat,lon;
    private int distance=5;

    LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home);
        try {

            locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
            isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissionsToRequest = findUnAskedPermissions(permissions);

            if (!isGPS && !isNetwork) {
                Log.d(TAG, "Connection off");
                showSettingsAlert();
                getLastLocation();
            } else {
                Log.d(TAG, "Connection on");
                // check permissions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (permissionsToRequest.size() > 0) {
                        requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                                ALL_PERMISSIONS_RESULT);
                        Log.d(TAG, "Permission requests");
                        canGetLocation = false;
                    }
                }

                // get location
                getLocation();
            }

            searchadapter =new SearchAdapter(this,data,Home.this);
            binding.searchRv.setLayoutManager(new GridLayoutManager(this,1));

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
                                if(BasicUtilities.checkNetworkConnection(Home.this)) {
                                    nearResturant(40.688072, -73.997385, distance);
//                                    nearResturant(lat, lon, distance);
                                }else{
                                    Toast.makeText(Home.this, R.string.Alert_Internet, Toast.LENGTH_SHORT).show();
                                }
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
//                nearResturant(lat, lon, distance);
                nearResturant(40.688072, -73.997385, distance);
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
            Log.d(TAGG,search_query.toString());
            Call<ResponsegetSearch> call = cr.getSearchByFiels(map,search_query);
            call.enqueue(new Callback<ResponsegetSearch>() {
                @Override
                public void onResponse(Call<ResponsegetSearch> call, Response<ResponsegetSearch> response) {
                    try{
                        binding.progressBar.setVisibility(View.GONE);
                        Log.d(TAG,response.body().toString());
                        if(response.body().getData().size() > 0 && response.code() == 200){
                            binding.searchRv.setVisibility(View.VISIBLE);
                            data=response.body().getData();
                            searchadapter.setData(data);
                            binding.searchRv.setAdapter(searchadapter);
                            searchadapter.update(data);
                        }else{
                            Toast.makeText(Home.this, R.string.resturant_nf, Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
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
        binding.progressBar.setVisibility(View.VISIBLE);
        ApiInterface api= APIClient.getClient().create(ApiInterface.class);
        HashMap<String,String> map=new HashMap<>();
        map.put("x-api-key", Globals.Key);
        Log.d(TAGG,"lat:"+lat+"long:"+lon+"dist:"+distance);
        Log.d(TAGG,Globals.Key.toString());
        Call<ResponsegetSearchDetails> call=api.getSearchGeo(map,lat,lon,distance);
        call.enqueue(new Callback<ResponsegetSearchDetails>() {
            @Override
            public void onResponse(Call<ResponsegetSearchDetails> call, Response<ResponsegetSearchDetails> response) {
                try{
                    binding.progressBar.setVisibility(View.GONE);

                    Log.d(TAG,response.body().toString());
                    if(response.body().getData().size() > 0 && response.code() == 200){
                        binding.resturantRv.setVisibility(View.VISIBLE);
                        binding.searchRv.setVisibility(View.GONE);
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
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        updateUI(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    private void getLocation() {
        try {
            if (canGetLocation) {
                Log.d(TAG, "Can get location");
                if (isGPS) {
                    // from GPS
                    Log.d(TAG, "GPS on");
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else if (isNetwork) {
                    // from Network Provider
                    Log.d(TAG, "NETWORK_PROVIDER on");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }
            } else {
                Log.d(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            Log.d(TAG, provider);
            Log.d(TAG, location == null ? "NO LastLocation" : location.toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                Log.d(TAG, "onRequestPermissionsResult");
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    (dialog, which) -> {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(permissionsRejected.toArray(
                                                    new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    Log.d(TAG, "No rejected permissions.");
                    canGetLocation = true;
                    getLocation();
                }
                break;
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Home.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void updateUI(Location loc) {
        Log.d(TAG, "updateUI");
        lat=loc.getLatitude();
        lon=loc.getLongitude();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onClickPosition(DataItem data, String name) {
        try{
            Intent i=new Intent(this,ResturantDetails.class);
            i.putExtra("restId",data.getRestaurantId());
            startActivity(i);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onclick(com.example.codegamatask.models.searchModel.DataItem data, String name) {
        try{
        Intent i=new Intent(this,ResturantDetails.class);
        i.putExtra("restId",data.getRestaurantId());
        startActivity(i);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}