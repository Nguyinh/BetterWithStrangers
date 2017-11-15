package ubiquasif.uqac.betterwithstrangers.Location;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

import ubiquasif.uqac.betterwithstrangers.MapActivity;
/**
 * Created by hugob on 11/11/2017.
 */

public class MyLocationManager implements LocationListener {

    private LocationManager locationManager;
    private Criteria criteria;
    private String provider;
    private Location currentLocation = null;
    private MapActivity mapActivity;

    public MyLocationManager(Context context, MapActivity mapActivity)
    {
        Log.d("MyLocationManager", "hello");
        this.mapActivity = mapActivity;

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        provider = locationManager.getBestProvider(criteria, true);

        try
        {
            locationManager.requestLocationUpdates(provider, 1000, 1, this);
        }

        catch (SecurityException e)
        {
            Log.d("gps", e.getMessage());
        }

    }

    @Override
    public void onLocationChanged(Location location)
    {
        Log.d("onLocationChanged", location.toString());
        if (location.hasAccuracy())
        {
            if (location.getAccuracy() < 10.0f)
            {
                Log.d("onLocationChanged", location.getLongitude() + " " + location.getLatitude());
                setCurrentLocation(location);
                locationManager.removeUpdates(this);
            }
        }
    }

    @Override
    public void onProviderDisabled(String arg0)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String arg0)
    {
        // TODO Auto-generated method stub

    }


    @Override
    public void onStatusChanged (String provider,
                                 int status,
                                 Bundle extras) {
        // TODO Auto-generated method stub
        Log.d("onStatusChanged",  provider + ", " + status + ", " + extras.toString());
    }

    public void launchRequests()
    {
        try
        {
            locationManager.requestLocationUpdates(provider, 1000, 1, this);
        }

        catch (SecurityException e)
        {
            Log.d("gps", e.getMessage());
        }
    }

    public void setCurrentLocation(Location location)
    {
        currentLocation = location;
        mapActivity.setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
        mapActivity.setView();
    }
}

