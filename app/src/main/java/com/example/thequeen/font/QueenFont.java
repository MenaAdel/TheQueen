package com.example.thequeen.font;

/**
 * Created by ahmed on 23-Dec-17.
 */

public enum QueenFont {
	ARABIC_FONT("Abdo Master Medium.otf");

	private String assetName;

	QueenFont(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetName() {
		return assetName;
	}

}
