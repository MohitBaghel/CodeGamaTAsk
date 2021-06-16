package com.example.codegamatask.models.byLocation;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MenuSectionsItem{

	@SerializedName("section_name")
	private String sectionName;

	@SerializedName("description")
	private String description;

	@SerializedName("menu_items")
	private List<MenuItemsItem> menuItems;

	public String getSectionName(){
		return sectionName;
	}

	public String getDescription(){
		return description;
	}

	public List<MenuItemsItem> getMenuItems(){
		return menuItems;
	}
}