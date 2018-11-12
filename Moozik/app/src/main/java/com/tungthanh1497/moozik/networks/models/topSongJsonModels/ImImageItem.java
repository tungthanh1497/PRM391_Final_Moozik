package com.tungthanh1497.moozik.networks.models.topSongJsonModels;

import com.google.gson.annotations.SerializedName;

public class ImImageItem{

	@SerializedName("attributes")
	private Attributes attributes;

	@SerializedName("label")
	private String label;

	public void setAttributes(Attributes attributes){
		this.attributes = attributes;
	}

	public Attributes getAttributes(){
		return attributes;
	}

	public void setLabel(String label){
		this.label = label;
	}

	public String getLabel(){
		return label;
	}

	@Override
 	public String toString(){
		return 
			"ImImageItem{" + 
			"attributes = '" + attributes + '\'' + 
			",label = '" + label + '\'' + 
			"}";
		}
}