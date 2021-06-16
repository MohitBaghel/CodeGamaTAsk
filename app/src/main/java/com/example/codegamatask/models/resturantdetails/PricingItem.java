package com.example.codegamatask.models.resturantdetails;

import com.google.gson.annotations.SerializedName;

public class PricingItem{

	@SerializedName("priceString")
	private String priceString;

	@SerializedName("price")
	private Double price;

	@SerializedName("currency")
	private String currency;

	public String getPriceString(){
		return priceString;
	}

	public Double getPrice() {
		return price;
	}

	public String getCurrency(){
		return currency;
	}
}