package com.example.codegamatask.models.resturantdetails;

import com.google.gson.annotations.SerializedName;

public class Address{

	@SerializedName("city")
	private String city;

	@SerializedName("street")
	private String street;

	@SerializedName("formatted")
	private String formatted;

	@SerializedName("state")
	private String state;

	@SerializedName("postal_code")
	private String postalCode;

	public String getCity(){
		return city;
	}

	public String getStreet(){
		return street;
	}

	public String getFormatted(){
		return formatted;
	}

	public String getState(){
		return state;
	}

	public String getPostalCode(){
		return postalCode;
	}
}