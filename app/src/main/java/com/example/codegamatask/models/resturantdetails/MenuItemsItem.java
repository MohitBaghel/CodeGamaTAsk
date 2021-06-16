package com.example.codegamatask.models.resturantdetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MenuItemsItem{

	@SerializedName("price")
	private Double price;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("pricing")
	private List<PricingItem> pricing;

	public Double getPrice() {
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