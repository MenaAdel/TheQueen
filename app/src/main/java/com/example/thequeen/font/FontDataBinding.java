package com.example.thequeen.font;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by ahmed on 23-Dec-17.
 */

public class FontDataBinding {
	@BindingAdapter("typeface")
	public static void setTypeface(TextView textView, QueenFont font) {
		if (font != null) {
			Typeface typeface = FontProvider.getInstance().getFont(font);
			textView.setTypeface(typeface);
		}
	}
}
