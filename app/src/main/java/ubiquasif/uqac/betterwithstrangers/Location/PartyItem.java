package ubiquasif.uqac.betterwithstrangers.Location;

import com.google.android.gms.maps.model.LatLng;

import ubiquasif.uqac.betterwithstrangers.maps.android.clustering.ClusterItem;

/**
 * Created by hugob on 14/11/2017.
 */

public class PartyItem implements ClusterItem
{
    private final LatLng mPosition;

    // Elements to display on the map and on click
    public final int profilePhoto;
    public final String placeName;
    public final String partyTags;

    public PartyItem(LatLng position, int pictureResource, String name,  String tags)
    {
        mPosition = position;
        profilePhoto = pictureResource;
        placeName = name;
        partyTags = tags;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle()
    {
        return null;
    }

    @Override
    public String getSnippet()
    {
        return null;
    }
}
