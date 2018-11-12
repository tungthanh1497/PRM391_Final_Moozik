package com.tungthanh1497.moozik.networks.models.musicTypeJsonModels;

import com.google.gson.annotations.SerializedName;

public class SubgenresItem{

	@SerializedName("translation_key")
	private String translationKey;

	@SerializedName("id")
	private String id;

	public void setTranslationKey(String translationKey){
		this.translationKey = translationKey;
	}

	public String getTranslationKey(){
		return translationKey;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"SubgenresItem{" + 
			"translation_key = '" + translationKey + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}