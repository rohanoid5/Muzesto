package com.rohan.app.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import com.jaeger.library.StatusBarUtil;
import com.rohan.app.R;
import com.rohan.app.nowplaying.Timber1;
import com.rohan.app.nowplaying.Timber2;
import com.rohan.app.nowplaying.Timber3;
import com.rohan.app.nowplaying.Timber4;
import com.rohan.app.nowplaying.Timber5;
import com.rohan.app.utils.Constants;
import com.rohan.app.utils.NavigationUtils;

/**
 * Created by naman on 01/01/16.
 */
public class NowPlayingActivity extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplaying);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
//        StatusBarUtil.setTranslucent(this);

        //setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        /*android.support.v7.app.ActionBar aBar = getSupportActionBar();
        aBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/

/*        ATE.config(this, null)
                .statusBarColor(Color.TRANSPARENT)
                .coloredNavigationBar(true)
                .navigationBarColor(Color.TRANSPARENT)
                .toolbarColor(Color.TRANSPARENT)
                .apply(this);*/

        SharedPreferences prefs = getSharedPreferences(Constants.FRAGMENT_ID, Context.MODE_PRIVATE);
        String fragmentID = prefs.getString(Constants.NOWPLAYING_FRAGMENT_ID, Constants.TIMBER3);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String unitType = sharedPrefs.getString(getString(R.string.pref_units_key), getString(R.string.pref_units_timber4));

        Fragment fragment = getFragmentForNowplayingID(unitType);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).commit();

    }

    public Fragment getFragmentForNowplayingID(String fragmentID) {
        switch (fragmentID) {
            case Constants.TIMBER1:
                return new Timber1();
            case Constants.TIMBER2:
                return new Timber2();
            case Constants.TIMBER3:
                return new Timber3();
            case Constants.TIMBER4:
                return new Timber4();
            case Constants.TIMBER5:
                return new Timber5();
            default:
                return new Timber4();
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        /*if (PreferencesUtility.getInstance(this).didNowplayingThemeChanged()) {
            PreferencesUtility.getInstance(this).setNowPlayingThemeChanged(false);
            recreate();
        }*/
    }

    /*@Override
    public int getLightToolbarMode(Toolbar toolbar) {
        return Config.LIGHT_TOOLBAR_ON;
    }

    @Override
    public int getToolbarColor(Toolbar toolbar) {
        return Color.TRANSPARENT;
    }*/
}
