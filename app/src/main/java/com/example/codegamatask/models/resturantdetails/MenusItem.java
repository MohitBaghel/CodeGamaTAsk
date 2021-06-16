package com.example.codegamatask.models.resturantdetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MenusItem{

	@SerializedName("menu_sections")
	private List<MenuSectionsItem> menuSections;

	@SerializedName("menu_name")
	private String menuName;

	public List<MenuSectionsItem> getMenuSections(){
		return menuSections;
	}

	public String getMenuName(){
		return menuName;
	}
}