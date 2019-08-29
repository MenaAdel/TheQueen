package com.example.thequeen.utils;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class DataBindingUtils {

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url != null) {
            Picasso.with(imageView.getContext())
                    .load(Uri.parse(url))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(imageView);
        }
    }

    @BindingAdapter("flagColor")
    public static void setStatusTextColor(Button textView, String flagColor) {
        if (flagColor.length() > 6)
            textView.setBackgroundColor(Color.parseColor(flagColor));
    }
}
