package com.example.codegamatask.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codegamatask.Interface.MVPView;
import com.example.codegamatask.databinding.LayoutResturantAdapterBinding;
import com.example.codegamatask.models.byLocation.DataItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResturantAdapter extends RecyclerView.Adapter<ResturantAdapter.ViewHolder> {

    Context context;
    List<DataItem> dataItems;
    MVPView mvpView;

    public ResturantAdapter(Context context, List<DataItem> dataItems, MVPView mvpView) {
        this.context = context;
        this.dataItems = dataItems;
        this.mvpView = mvpView;
    }

    @NonNull
    @NotNull
    @Override
    public ResturantAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        LayoutResturantAdapterBinding binding=LayoutResturantAdapterBinding.inflate(inflater,parent,false);
        return  new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ResturantAdapter.ViewHolder holder, int position) {
        DataItem data=dataItems.get(position);
        holder.binding.restNameTv.setText(data.getRestaurantName());

    }

    @Override
    public int getItemCount() {
        if (dataItems != null) {
            return dataItems.size();
        } else {
            return 0;
        }
    }
    public void update(List<DataItem> dataItems) {
        try {
            this.dataItems = dataItems;
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(List<DataItem> dataItems) {
            this.dataItems=dataItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutResturantAdapterBinding binding;
        public ViewHolder(@NonNull @NotNull  LayoutResturantAdapterBinding binding) {
            super(binding.getRoot());
            try{
             this.binding=binding;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
