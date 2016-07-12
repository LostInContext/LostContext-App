package com.lostincontext.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by STrabelsi on 12/07/2016.
 */

public class LocationUtils {

    @Nullable
    public static LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            String[] addressArr = new String[address.size()];

            for (int i = 0; i < address.size(); i++) {
                addressArr[i] = String.format("%s, %s, %s",
                                              address.get(i).getMaxAddressLineIndex() > 0 ? address.get(i).getAddressLine(0) : "",
                                              address.get(i).getLocality(),
                                              address.get(i).getCountryName());

            }

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Make your selection");
            builder.setItems(addressArr, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    // Do something with the selection
                }
            });
            AlertDialog alert = builder.create();
            alert.show();


//            Address location = address.get(0);
//            location.getLatitude();
//            location.getLongitude();
//
//            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

}
