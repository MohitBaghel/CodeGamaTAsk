package com.example.codegamatask.models.searchModel;

import com.google.gson.annotations.SerializedName;

public class Geo{

	@SerializedName("lon")
	private double lon;

	@SerializedName("lat")
	private double lat;

	public double getLon(){
		return lon;
	}

	public double getLat(){
		return lat;
	}
}