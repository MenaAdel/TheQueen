package com.example.thequeen.utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mina-pc on 7/13/2017.
 */

public class CSnackbar {

    public static class SnackBuilder {
        Snackbar snackbar;
        View view;
        String message;
        int duration = 0;
        String actionButtonText;
        int messageTextColor = 0;
        int messageTextSize = 0;
        int backgroudColor = 0;

        public SnackBuilder(View view, String message) {
            this.view = view;
            this.message = message;
        }

        public SnackBuilder setMessageColor(int messageTextColor) {
            this.messageTextColor = messageTextColor;
            return this;
        }

        public SnackBuilder setDuration(int duration) {
            this.duration = duration;
            return this;

        }

        public SnackBuilder setMessageTextSize(int messageTextSize) {
            this.messageTextSize = messageTextSize;
            return this;
        }

        public SnackBuilder setBackroundColor(int backgroudColor) {
            this.backgroudColor = backgroudColor;
            return this;
        }

        public Snackbar buildSnackBar() {
            if (duration != 0) {
                snackbar = Snackbar.make(this.view, message, duration);
            } else {
                snackbar = Snackbar.make(this.view, message, Snackbar.LENGTH_SHORT);
            }

            if (this.messageTextColor != 0) {
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(messageTextColor);
                textView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
            if (this.messageTextSize != 0) {
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextSize(messageTextSize);
                textView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }
            if (this.backgroudColor != 0) {
                View sbView = snackbar.getView();
                sbView.setBackgroundColor(backgroudColor);
            }
            return snackbar;
        }

    }
}

