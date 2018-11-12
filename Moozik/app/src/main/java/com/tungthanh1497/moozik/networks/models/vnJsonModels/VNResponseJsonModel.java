package com.tungthanh1497.moozik.networks.models.vnJsonModels;

import com.google.gson.annotations.SerializedName;

public class VNResponseJsonModel {

	@SerializedName("feed")
	private Feed feed;

	public void setFeed(Feed feed){
		this.feed = feed;
	}

	public Feed getFeed(){
		return feed;
	}

	@Override
 	public String toString(){
		return 
			"VNResponseJsonModel{" +
			"feed = '" + feed + '\'' + 
			"}";
		}
}