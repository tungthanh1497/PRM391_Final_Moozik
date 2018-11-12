package com.tungthanh1497.moozik.networks.models.musicTypeJsonModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MusicTypeResponseJsonModel{

	@SerializedName("subgenres")
	private List<SubgenresItem> subgenres;

	public void setSubgenres(List<SubgenresItem> subgenres){
		this.subgenres = subgenres;
	}

	public List<SubgenresItem> getSubgenres(){
		return subgenres;
	}

	@Override
 	public String toString(){
		return 
			"MusicTypeResponseJsonModel{" + 
			"subgenres = '" + subgenres + '\'' + 
			"}";
		}
}