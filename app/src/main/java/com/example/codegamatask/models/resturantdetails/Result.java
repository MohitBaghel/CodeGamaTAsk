package com.example.codegamatask.models.resturantdetails;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("geo")
	private Geo geo;

	@SerializedName("hours")
	private String hours;

	@SerializedName("last_updated")
	private String lastUpdated;

	@SerializedName("address")
	private Address address;

	@SerializedName("restaurant_phone")
	private String restaurantPhone;

	@SerializedName("restaurant_id")
	private long restaurantId;

	@SerializedName("price_range_num")
	private Double priceRangeNum;

	@SerializedName("price_range")
	private String priceRange;

	@SerializedName("menus")
	private List<MenusItem> menus;

	@SerializedName("restaurant_website")
	private String restaurantWebsite;

	@SerializedName("restaurant_name")
	private String restaurantName;

	@SerializedName("cuisines")
	private List<String> cuisines;

	public Geo getGeo(){
		return geo;
	}

	public String getHours(){
		return hours;
	}

	public String getLastUpdated(){
		return lastUpdated;
	}

	public Address getAddress(){
		return address;
	}

	public String getRestaurantPhone(){
		return restaurantPhone;
	}

	public long getRestaurantId(){
		return restaurantId;
	}



	public String getPriceRange(){
		return priceRange;
	}

	public List<MenusItem> getMenus(){
		return menus;
	}

	public String getRestaurantWebsite(){
		return restaurantWebsite;
	}

	public String getRestaurantName(){
		return restaurantName;
	}

	public Double getPriceRangeNum() {
		return priceRangeNum;
	}

	public List<String> getCuisines(){
		return cuisines;
	}
}