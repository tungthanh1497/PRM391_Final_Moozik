package com.tungthanh1497.moozik.networks.models.topSongJsonModels;

import com.google.gson.annotations.SerializedName;

public class TopSongResponseJsonModel{

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
			"TopSongResponseJsonModel{" + 
			"feed = '" + feed + '\'' + 
			"}";
		}
}