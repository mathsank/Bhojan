package bhojan.chennai;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mathangi on 10-09-2016.
 */
public class Map extends Fragment implements OnMapReadyCallback, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    GoogleMap mGooglemap;
    Marker marker;
    View view1;
    GoogleApiClient mGoogleApiClient;
    List<android.location.Address> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        view1 = view.findViewById(R.id.button2);
        view1.setOnClickListener(this);
        //get marker details
        return view;
    }

    @Override
    public void onClick(View view) {

        try {
            geoLocate(view1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    EditText et;

    public void geoLocate(View view) throws IOException, InterruptedException {
        et = (EditText) getActivity().findViewById(R.id.editText1);
        String name = et.getText().toString();
        //Toast.makeText(Map.this.getActivity(), R.string.accept, Toast.LENGTH_LONG).show();
        Geocoder gc = new Geocoder(Map.this.getActivity());
        List<android.location.Address> list = gc.getFromLocationName(name, 1);
        android.location.Address address = list.get(0);
        String locality = address.getLocality();
        Toast.makeText(Map.this.getActivity(), locality, Toast.LENGTH_LONG).show();
        double lat = address.getLatitude();
        double lng = address.getLongitude();
        goToLocationZoom(lat,lng,15);
        //setMarker( lat, lng);



    }

    private void setMarker( double lat, double lng) throws IOException {
       // if(marker != null){
          //  marker.remove();
        //}

        MarkerOptions options = new MarkerOptions()
              // .title(locality)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                .position(new LatLng(lat,lng))
                .draggable(true)
                .snippet("ppl here");

        marker = mGooglemap.addMarker(options);

        //send marker details to table
    }
    Marker[] marker1 = new Marker[100];
    static int count = -1;
    private void setMarker1( double lat, double lng) {

        MarkerOptions options = new MarkerOptions()
                //.title(locality)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                .position(new LatLng(lat,lng))
                .draggable(false)
                .snippet("ppl here");

        marker1[count]= mGooglemap.addMarker(options);
        count++;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
        for(int i=0;i<count;i++)
        {
            LatLng ll = marker1[count].getPosition();
            setMarker1(ll.latitude,ll.longitude);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGooglemap = googleMap;

       /* mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect()*/


        if(mGooglemap!=null)
        {

            mGooglemap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng point) {
                    try {
                        setMarker(point.latitude,point.longitude);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });





            mGooglemap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {
                    marker.remove();//comment this to make marker draggable, currently just removes the existing marker on long click

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    Geocoder gc = new Geocoder(Map.this.getActivity());
                    LatLng ll = marker.getPosition();
                    List<Address> list= null;
                    try {
                        list = gc.getFromLocation(ll.latitude,ll.longitude,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address add = list.get(0);
                    marker.setTitle(add.getLocality());
                    marker.showInfoWindow();

                }
            });



            mGooglemap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){

                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getActivity().getLayoutInflater().inflate(R.layout.custom_info_window,null);
                    TextView tvLocality = (TextView) v.findViewById(R.id.tv_locality);
                    TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                    TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
                    TextView tvSnippet = (TextView) v.findViewById(R.id.tv_snippet);
                    LatLng ll = marker.getPosition();
                    //tvLocality.setText(marker.getTitle());
                   //Toast.makeText(Map.this.getActivity(), marker.getTitle(), Toast.LENGTH_LONG).show();
                    tvLat.setText("Latitude: "+ (int)ll.latitude);
                    tvLng.setText("Longitude: "+ (int)ll.longitude);
                    tvSnippet.setText(marker.getSnippet());

                    return v;
                }
            });
        }
        goToLocationZoom(13.0827, 80.2707, 15);
    }
//Nothing after this
    private void goToLocationZoom(double lat, double lng, int zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGooglemap.animateCamera(update);
    }




    LocationRequest mLocationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);
        if (ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
    if(location == null){
        Toast.makeText(Map.this.getActivity(),"Can't get Location",Toast.LENGTH_LONG).show();

    }
        else
    {
        LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,15);
        mGooglemap.animateCamera(update);
    }

    }
}
