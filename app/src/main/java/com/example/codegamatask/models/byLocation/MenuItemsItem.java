package com.example.codegamatask.models.byLocation;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MenuItemsItem{

	@SerializedName("price")
	private double price;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("pricing")
	private List<PricingItem> pricing;

	public double getPrice(){
		return price;
	}

	public String getName(){
		return name;
	}

	public String getDescription(){
		return description;
	}

	public List<PricingItem> getPricing(){
		return pricing;
	}
}