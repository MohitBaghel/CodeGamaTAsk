package com.example.codegamatask.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codegamatask.Interface.ItemSelected;
import com.example.codegamatask.databinding.LayoutMenuitemDetailsBinding;
import com.example.codegamatask.databinding.LayoutMenuitemDetailsBinding;
import com.example.codegamatask.models.resturantdetails.MenuItemsItem;
import com.example.codegamatask.models.resturantdetails.MenuSectionsItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MenuDetailAdapter extends RecyclerView.Adapter<MenuDetailAdapter.ViewHolder> {
    Context context;
    List<MenuItemsItem> menu;


    public MenuDetailAdapter(Context context, List<MenuItemsItem> menu) {
        this.context = context;
        this.menu = menu;
    }

    @NonNull
    @NotNull
    @Override
    public MenuDetailAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        LayoutMenuitemDetailsBinding binding=LayoutMenuitemDetailsBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MenuDetailAdapter.ViewHolder holder, int position) {
            MenuItemsItem item=menu.get(position);
            holder.binding.restNameTv.setText(item.getName());
            holder.binding.hour.setText(item.getDescription());
            holder.binding.price.setText(String.valueOf("$ "+item.getPrice()));
    }

    @Override
    public int getItemCount() {
        if (menu != null) {
            return menu.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutMenuitemDetailsBinding binding;
        public ViewHolder(@NonNull @NotNull  LayoutMenuitemDetailsBinding binding) {
            super(binding.getRoot());
            try{
                this.binding=binding;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
