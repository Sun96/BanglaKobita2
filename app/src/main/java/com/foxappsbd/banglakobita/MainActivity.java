package com.foxappsbd.banglakobita;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.foxappsbd.banglakobita.activity.HomeFragment;
import com.foxappsbd.banglakobita.activity.MusicFragment;
import com.foxappsbd.banglakobita.activity.VideoFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    int a;
    private long lastPressedTime;
    private PublisherInterstitialAd interstitialAd;
    private InterstitialAd interstitial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (a==0) {
            displayView(0);
            a=1;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (item.getItemId()) {
            case R.id.nav_camera:
                fragment = new HomeFragment();
                title = "Import";
                drawer.closeDrawers();
                break;
            case R.id.nav_gallery:
                fragment = new MusicFragment();
                title = "Gallery";
                drawer.closeDrawers();
                break;
            case R.id.nav_slideshow:
                fragment = new VideoFragment();
                title = "Slideshow";
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
            default:
                break;
        }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = "Import";
                drawer.closeDrawers();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
    //for add..........................
    private void requestNewInterstitial() {
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder()
                .addTestDevice("")
                .build();

        interstitialAd.loadAd(adRequest);
    }
    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
    {
        int i = 0;
        boolean bool = true;
        if (paramKeyEvent.getKeyCode() == 4) {
            switch (paramKeyEvent.getAction())
            {
                case 0:
                    if (paramKeyEvent.getDownTime() - this.lastPressedTime >= 3000L)
                    {
                        Toast.makeText(getApplicationContext(), "Press Back To Exit", 0).show();
                        this.interstitial = new InterstitialAd(this);
                        this.interstitial.setAdUnitId("ca-app-pub-3420675499646666/1093565039");
                        AdRequest localAdRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("CF2B5D5C45BA785670BBCAEA9269EA35").build();
                        this.interstitial.loadAd(localAdRequest);
                        this.interstitial.setAdListener(new AdListener()
                        {
                            private void displayInterstitial()
                            {
                                if (MainActivity.this.interstitial.isLoaded()) {
                                    MainActivity.this.interstitial.show();
                                }
                            }

                            public void onAdLoaded()
                            {
                                displayInterstitial();
                            }
                        });
                        this.lastPressedTime = paramKeyEvent.getEventTime();
                    }
                    else
                    {
                        finish();
                    }
                    bool = true;
            }
        }
        return bool;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu paramMenu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, paramMenu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {

        switch (paramMenuItem.getItemId())
        {
            default:
                super.onOptionsItemSelected(paramMenuItem);
            case R.id.rate_app:
                Toast.makeText(getApplicationContext(), "Rate This App", Toast.LENGTH_SHORT).show();
                super.onOptionsItemSelected(paramMenuItem);
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.foxappsbd.semaipayesrecipes")));
                break;
            case R.id.share_app:
                Intent localIntent = new Intent("android.intent.action.SEND");
                localIntent.setType("text/plain");
                localIntent.putExtra("android.intent.extra.TEXT", "Enjoy This Apps https://play.google.com/store/apps/details?id=com.foxappsbd.semaipayesrecipes");
                startActivity(Intent.createChooser(localIntent, "Share This App Using"));
                break;
            case R.id.more_app:
                Toast.makeText(getApplicationContext(), "More Apps", Toast.LENGTH_SHORT).show();
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=FoxAppsBd")));
                break;
            case R.id.exit:
                finish();
                System.exit(0);
        }
        return true;
    }
}
