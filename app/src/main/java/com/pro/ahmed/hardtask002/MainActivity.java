package com.pro.ahmed.hardtask002;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.pro.ahmed.hardtask002.fragments.RegistrationFragment;
import com.pro.ahmed.hardtask002.fragments.WebViewFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static String checkLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // start registration fragment
        checkLocal = Locale.getDefault().getISO3Language();
        Fragment fragment = new RegistrationFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (checkLocal.equals("ara")) { // start from right to left
            fragmentTransaction.setCustomAnimations(R.anim.in_from_left, R.anim.out_to_right, R.anim.in_from_right, R.anim.out_to_left);
        } else { // start from left to right
            fragmentTransaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        }
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (WebViewFragment.getMyWebView() != null) {
            if ((keyCode == KeyEvent.KEYCODE_BACK) && WebViewFragment.getMyWebView().canGoBack()) {
                WebViewFragment.getMyWebView().goBack(); // to Navigation for back
                return true;
            } else {
                RegistrationFragment.setIsFragmentBack(false); // set IsSpinnerInitial to false ;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
