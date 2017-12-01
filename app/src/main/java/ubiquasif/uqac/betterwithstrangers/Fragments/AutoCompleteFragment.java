package ubiquasif.uqac.betterwithstrangers.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import ubiquasif.uqac.betterwithstrangers.R;

/**
 * Created by hugob on 30/11/2017.
 */

public class AutoCompleteFragment extends PlaceAutocompleteFragment
{
    AutocompleteFilter typeFilter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        this.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.d("YOLO", "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.d("error place", "An error occurred: " + status);
            }
        });

        typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT)
                .build();

        this.setFilter(typeFilter);
        this.setBoundsBias(new LatLngBounds(new LatLng(48.315341, -71.216323),
                                            new LatLng(48.459891, -70.940291)));
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
