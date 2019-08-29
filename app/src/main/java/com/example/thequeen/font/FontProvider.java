package com.example.thequeen.font;

import android.content.Context;
import android.graphics.Typeface;

import java.util.EnumMap;

/**
 * Created by ahmed on 23-Dec-17.
 */

public class FontProvider {

	private static FontProvider instance = new FontProvider();
	private Context appContext;

	// a performance optimized map.
	private EnumMap<QueenFont, Typeface> fontsMap = new EnumMap<>(QueenFont.class);

	public static FontProvider getInstance() {
		return instance;
	}

	private FontProvider() {
	}

	public void init(Context context) {
		this.appContext = context.getApplicationContext();
	}

	public Typeface getFont(QueenFont font) {
		// if it doesn't exist in the map (not loaded before), load it then return it.
		if (!fontsMap.containsKey(font)) {
			Typeface typeface = Typeface.createFromAsset(appContext.getAssets(), "fonts/" + font.getAssetName());
			fontsMap.put(font, typeface);
		}
		// lastly, we are sure the font is in the map.
		return fontsMap.get(font);
	}
}
