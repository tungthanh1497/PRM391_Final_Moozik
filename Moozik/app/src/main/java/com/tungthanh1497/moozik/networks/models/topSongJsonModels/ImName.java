package com.tungthanh1497.moozik.networks.models.topSongJsonModels;

import com.google.gson.annotations.SerializedName;

public class ImName{

	@SerializedName("label")
	private String label;

	public void setLabel(String label){
		this.label = label;
	}

	public String getLabel(){
		return label;
	}

	@Override
 	public String toString(){
		return 
			"ImName{" + 
			"label = '" + label + '\'' + 
			"}";
		}
}