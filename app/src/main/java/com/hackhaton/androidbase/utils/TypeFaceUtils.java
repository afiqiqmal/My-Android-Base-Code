package com.hackhaton.androidbase.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @author : hafiq on 01/09/2017.
 */

public class TypeFaceUtils {

    public static Typeface getBoldFont(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/circularstd-bold.ttf");
    }
}
