package com.example.collabbees;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {

    /** Checks if user detail is empty or null and sets default text if it is */
    public static void setTextView(TextView tv, String text) {
        // check for empty String and whitespace only String
        if (text == null) {
            tv.setText(tv.getText());
        } else {
            if (!text.isEmpty() && text.trim().length() > 0) {
                tv.setText(text);
            } else {
                tv.setText(tv.getText());
            }
        }
    }

    /** Set uri image for shapeable image view */
    public static void setImageView(ShapeableImageView view, String urlProfilePic, Context context) {
        if (urlProfilePic != null) {
            Glide.with(context)
                    .load(Uri.parse(urlProfilePic))
                    .apply(RequestOptions.fitCenterTransform())
                    .into(view);
        }
    }

    /** Convert LocalDateTime Object to String Object */
    // TODO: Create DateTime for lower build versions //
    public static String formatDate(Date timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return dateFormat.format(timestamp);
    }

    /** Sorts a list in lexicographically order */
    public static String[] sortLexicographically (String[] list) {
        for(int i = 0; i < list.length-1; i++) {
            for (int j = i + 1; j < list.length; ++j) {
                if (list[i].compareTo(list[j]) > 0) {
                    String uid = list[i];
                    list[i] = list[j];
                    list[j] = uid;
                }
            }
        }
        return list;
    }
}
