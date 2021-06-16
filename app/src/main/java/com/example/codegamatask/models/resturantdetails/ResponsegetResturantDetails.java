package com.example.codegamatask.models.resturantdetails;

import com.google.gson.annotations.SerializedName;

public class ResponsegetResturantDetails{

	@SerializedName("result")
	private Result result;

	public Result getResult(){
		return result;
	}
}