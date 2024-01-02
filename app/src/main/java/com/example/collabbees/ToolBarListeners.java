package com.example.collabbees;

import android.app.Activity;
import android.widget.ImageButton;

public class ToolBarListeners {

    public static void btnReturn(ImageButton btn, Activity activity){
        btn.setOnClickListener(view -> { activity.finish(); });
    }
}
