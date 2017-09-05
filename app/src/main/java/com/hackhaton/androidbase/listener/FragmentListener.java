package com.hackhaton.androidbase.listener;

import android.support.v4.app.Fragment;

/**
 * @author : hafiq on 02/09/2017.
 */

public class FragmentListener {

    public interface ExitCallback{
        public void onExit(Fragment fragment, int position);
    }


}
