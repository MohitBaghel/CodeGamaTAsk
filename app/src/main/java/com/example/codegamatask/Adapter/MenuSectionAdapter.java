package com.example.codegamatask.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codegamatask.Activity.ResturantDetails;
import com.example.codegamatask.Interface.ItemSelected;
import com.example.codegamatask.databinding.MenuItemsBinding;
import com.example.codegamatask.databinding.MenuItemsBinding;
import com.example.codegamatask.models.resturantdetails.MenuItemsItem;
import com.example.codegamatask.models.resturantdetails.MenuSectionsItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MenuSectionAdapter extends RecyclerView.Adapter<MenuSectionAdapter.ViewHolder> {

    Context context;
    List<MenuSectionsItem> menu;

    public MenuSectionAdapter(Context context, List<MenuSectionsItem> menu) {
        this.context = context;
        this.menu = menu;
    }


    @NonNull
    @NotNull
    @Override
    public MenuSectionAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        MenuItemsBinding binding=MenuItemsBinding.inflate(layoutInflater,parent,false);
        return  new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MenuSectionAdapter.ViewHolder holder, int position) {

        MenuSectionsItem sectionsItem = menu.get(position);
        holder.binding.beverage.setText(sectionsItem.getSectionName());
        holder.binding.description.setText(sectionsItem.getDescription());
        holder.binding.down.setOnClickListener(v -> {
            holder.binding.menuItemRv.setVisibility(View.VISIBLE);
            holder.binding.down.setVisibility(View.GONE);
            holder.binding.up.setVisibility(View.VISIBLE);
        });
        holder.binding.up.setOnClickListener(v -> {
            holder.binding.menuItemRv.setVisibility(View.GONE);
            holder.binding.down.setVisibility(View.VISIBLE);
            holder.binding.up.setVisibility(View.GONE);
        });
        if(sectionsItem.getMenuItems().size()>0 ){
            MenuDetailAdapter menuDetailAdapter=new MenuDetailAdapter(context,sectionsItem.getMenuItems());
            GridLayoutManager manager = new GridLayoutManager(context, 1);
            holder.binding.menuItemRv.setLayoutManager(manager);
            holder.binding.menuItemRv.setAdapter(menuDetailAdapter);
            menuDetailAdapter.notifyDataSetChanged();

        }


    }

    @Override
    public int getItemCount() {
        if (menu != null) {
            return menu.size();
        } else {
            return 0;
        }
    }
    public void update(List<MenuSectionsItem> menu) {
        try {
            this.menu = menu;
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setData(List<MenuSectionsItem> menu) {
        this.menu=menu;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        MenuItemsBinding binding;
        public ViewHolder(@NonNull @NotNull MenuItemsBinding binding) {
            super(binding.getRoot());
            try{
                this.binding=binding;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
