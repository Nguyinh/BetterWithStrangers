package ubiquasif.uqac.betterwithstrangers;
import android.*;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import ubiquasif.uqac.betterwithstrangers.Location.MyLocationManager;

/**
 * Created by hugob on 13/11/2017.
 */

public class MapActivity  extends AppCompatActivity
        implements OnMapReadyCallback
{

    private MyLocationManager myLocationManager;
    private LatLng userLatLng;
    private LatLngBounds userBoundary;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("MapActivity", "hello");
        myLocationManager = new MyLocationManager(this.getApplicationContext(), this);
        Log.d("MapActivity", "hello");
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    // Include the OnCreate() method here too, as described above.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            //googleMap.setMyLocationEnabled(true);
        }
    }

    public void setLatLng(LatLng latLng)
    {
        this.userLatLng = latLng;
    }

    public void setView()
    {
        googleMap.addMarker(new MarkerOptions().position(userLatLng)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLng));
    }

}
