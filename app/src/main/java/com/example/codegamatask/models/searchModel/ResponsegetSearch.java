package com.example.codegamatask.models.searchModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponsegetSearch{

	@SerializedName("totalResults")
	private int totalResults;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("numResults")
	private int numResults;

	@SerializedName("more_pages")
	private boolean morePages;

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	public int getTotalResults(){
		return totalResults;
	}

	public List<DataItem> getData(){
		return data;
	}

	public int getNumResults(){
		return numResults;
	}

	public boolean isMorePages(){
		return morePages;
	}

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}
}