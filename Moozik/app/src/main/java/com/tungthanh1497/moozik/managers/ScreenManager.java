package com.tungthanh1497.moozik.managers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tungthanh1497.moozik.MainActivity;
import com.tungthanh1497.moozik.R;

public class ScreenManager {


    public static void openFragmentMoveUp(FragmentManager fragmentManager, Fragment fragment, int layoutID, Context context) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down);

        fragmentTransaction.add(layoutID, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
//        Animation animFabMoveUp = AnimationUtils.loadAnimation(context, R.anim.fab_move_up);
//        MainActivity.fabPlayPause.setAnimation(animFabMoveUp);
        MainActivity.isMain = false;

        Log.d("14971497", "openFragmentMoveUp: "+fragmentManager.getBackStackEntryCount());
    }

    public static void openFragment(FragmentManager fragmentManager, Fragment fragment, int layoutID) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down);
        fragmentTransaction.add(layoutID, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        MainActivity.isMain = false;
        Log.d("14971497", "openFragmentMoveUp: "+fragmentManager.getBackStackEntryCount());

    }

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int layoutID) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutID, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
