package bhojan.chennai;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static bhojan.chennai.R.id.map;

public class LocateSpot extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    SupportMapFragment sMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(googleServicesAvailable())
        {
            Toast.makeText(this,"Perfect",Toast.LENGTH_LONG).show();

        }
        sMapFragment = SupportMapFragment.newInstance();
        setContentView(R.layout.activity_locate_spot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new Map()).commit();
        sMapFragment.getMapAsync(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(LocateSpot.this, LocateSpot.class);
            startActivity(intent);

        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        if(sMapFragment.isAdded())
            sFm.beginTransaction().hide(sMapFragment).commit();
        if (id == R.id.nav_profile) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();

        } else if (id == R.id.nav_contact) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new ContactFragment()).commit();

        } else if (id == R.id.nav_logout) {

        } else if (id == R.id.nav_map) {

            if(!sMapFragment.isAdded()) {
                sFm.beginTransaction().add(map, sMapFragment).commit();
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new Map()).commit();
            }
            else
                sFm.beginTransaction().show(sMapFragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap){

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.logo)
                    .setTitle("Bhojan")
                    .setMessage("Do you really want to exit the app?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Stop the activity
                            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                            homeIntent.addCategory(Intent.CATEGORY_HOME);
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeIntent);

                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        } else {
            return super.onKeyDown(
                    keyCode, event);
        }}

    public boolean googleServicesAvailable()
    {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable== ConnectionResult.SUCCESS)
        {
            return true;
        }
        else if(api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this,isAvailable,0);
            dialog.show();
        }
        else
        {
            Toast.makeText(this,"Can't Connect to play services",Toast.LENGTH_LONG).show();
        }
        return false;

    }
    }


    /*public void geoLocate(View view) {EditText et = (EditText) findViewById(R.id.editText);
        String location = et.getText().toString();
        Geocoder gc = new Geocoder(this);
        List<android.location.Address> list = gc.getFromLocationName(location,3);
        android.location.Address address= list.get(0);
        String locality = address.getLocality();
        Toast.makeText(this,locality,Toast.LENGTH_LONG).show();
        double lat = address.getLatitude();
        double lng = address.getLongitude();
        goToLocationZoom(lat,lng,15);
    }
    private void goTolocation(double lat, double lng)
    {
        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);

    }
    private void goToLocationZoom(double lat, double lng, int i) {
    }*/


