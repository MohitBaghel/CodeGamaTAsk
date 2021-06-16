package com.example.codegamatask.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codegamatask.Interface.ItemSelected;
import com.example.codegamatask.Interface.MVPView;
import com.example.codegamatask.databinding.LayoutResturantAdapterBinding;
import com.example.codegamatask.models.byLocation.DataItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    Context context;
    List<com.example.codegamatask.models.searchModel.DataItem> dataItem;
    ItemSelected itemSelected;

    public SearchAdapter(Context context, List<com.example.codegamatask.models.searchModel.DataItem> dataItem, ItemSelected itemSelected) {
        this.context = context;
        this.dataItem = dataItem;
        this.itemSelected = itemSelected;
    }

    @NonNull
    @NotNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        LayoutResturantAdapterBinding binding=LayoutResturantAdapterBinding.inflate(inflater,parent,false);
        return  new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchAdapter.ViewHolder holder, int position) {
        com.example.codegamatask.models.searchModel.DataItem data=dataItem.get(position);
        holder.binding.restNameTv.setText(data.getRestaurantName());
        holder.binding.price.setText(String.valueOf(data.getPriceRange()));
        holder.binding.priceNum.setText(String.valueOf(data.getPriceRangeNum()));
        holder.binding.hour.setText(String.valueOf("ph:"+data.getRestaurantPhone()));
        holder.binding.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSelected.onclick(data,"restId");
            }
        });
    }
    
    @Override
    public int getItemCount() {
        if (dataItem != null) {
            return dataItem.size();
        } else {
            return 0;
        }
    }
    public void update(List<com.example.codegamatask.models.searchModel.DataItem> dataItem) {
        try {
            this.dataItem = dataItem;
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setData(List<com.example.codegamatask.models.searchModel.DataItem> dataItem) {
        this.dataItem=dataItem;
    }
    

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutResturantAdapterBinding binding;
        public ViewHolder(@NonNull @NotNull LayoutResturantAdapterBinding binding) {
            super(binding.getRoot());
            try{
                this.binding=binding;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}