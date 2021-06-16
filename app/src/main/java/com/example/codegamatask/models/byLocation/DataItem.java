package com.example.codegamatask.models.byLocation;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

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
	private int priceRangeNum;

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

	public int getPriceRangeNum(){
		return priceRangeNum;
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

	public List<String> getCuisines(){
		return cuisines;
	}

	@Override
	public String toString() {
		return "DataItem{" +
				"geo=" + geo +
				", hours='" + hours + '\'' +
				", lastUpdated='" + lastUpdated + '\'' +
				", address=" + address +
				", restaurantPhone='" + restaurantPhone + '\'' +
				", restaurantId=" + restaurantId +
				", priceRangeNum=" + priceRangeNum +
				", priceRange='" + priceRange + '\'' +
				", menus=" + menus +
				", restaurantWebsite='" + restaurantWebsite + '\'' +
				", restaurantName='" + restaurantName + '\'' +
				", cuisines=" + cuisines +
				'}';
	}
}