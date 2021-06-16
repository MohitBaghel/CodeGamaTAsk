package com.example.codegamatask.models.byLocation;

import com.google.gson.annotations.SerializedName;

public class PricingItem{

	@SerializedName("priceString")
	private String priceString;

	@SerializedName("price")
	private double price;

	@SerializedName("currency")
	private String currency;

	public String getPriceString(){
		return priceString;
	}

	public double getPrice(){
		return price;
	}

	public String getCurrency(){
		return currency;
	}
}