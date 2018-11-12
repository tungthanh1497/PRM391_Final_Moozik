package com.tungthanh1497.moozik.networks.models.topSongJsonModels;

import com.google.gson.annotations.SerializedName;

public class Attributes {

	@SerializedName("height")
	private String height;

	@Override
	public String toString() {
		return "Attributes{" +
				"height='" + height + '\'' +
				'}';
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}