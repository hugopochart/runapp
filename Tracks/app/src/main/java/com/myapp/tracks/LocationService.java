package com.myapp.tracks;

/**
 * Created by hugo on 22/04/17.
 */

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class LocationService extends Service implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final long INTERVAL = 1000 * 2;
    private static final long FASTEST_INTERVAL = 1000 * 1;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation, lStart, lEnd;
    public static double distance = 0;
    public static String distancereturn ="";
    static int nombreKilometre = 4;
    double speed;
    double timerkilo0 = 0;
    double speedkilo = 0;

    GoogleMap map;

    public static List<String> storetime = new ArrayList<>();
    public static List<String> storetaveragespeed = new ArrayList<>();
    public static Map<String,String> storespeedandtime = new HashMap<String, String>();

    double distanceafter = 0;
    long time0 = 0;
    double distance0 = 0;




    private final IBinder mBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
        return mBinder;
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onConnected(Bundle bundle) {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } catch (SecurityException e) {
        }
    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        distance = 0;
    }


    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onLocationChanged(Location location) {
        MainActivity.locate.dismiss();
        mCurrentLocation = location;
        if (lStart == null) {
            lStart = mCurrentLocation;
            lEnd = mCurrentLocation;
        } else
            lEnd = mCurrentLocation;

        // the method  updates the  live values
        updateKilometer();
        speed = location.getSpeed() * 18 / 5;

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public class LocalBinder extends Binder {

        public LocationService getService() {
            return LocationService.this;
        }


    }

    // Distance and Speed and average speed are being set in the method below .
    private void updateKilometer() {

        if (MainActivity.p == 0) {
            distance = distance + (lStart.distanceTo(lEnd) / 1000.00);
            MainActivity.endTime = System.currentTimeMillis();
            long diff = (MainActivity.endTime  - MainActivity.startTime) ;
            diff = diff - MainActivity.pauserrrr;
            long diffaaa = TimeUnit.MILLISECONDS.toSeconds((MainActivity.pauserrrr));

            long totalseconde = TimeUnit.MILLISECONDS.toSeconds(diff);
            totalseconde = totalseconde - diffaaa;
            long totalheure = TimeUnit.MILLISECONDS.toHours(diff);
            long seconde = totalseconde % 60;
            long min = (totalseconde / 60) % 60;
            if (seconde == 60){seconde = 0;}
           // diff = TimeUnit.MILLISECONDS.toMinutes(diff);

            MainActivity.time.setText(min +":"+seconde);
            if (speed > 0.0)
                MainActivity.speed.setText(new DecimalFormat("#.#").format(speed));
            else
                MainActivity.speed.setText("0.0");


            DecimalFormat df = new DecimalFormat("#.##");
            MainActivity.dist.setText(String.valueOf(df.format(distance)));

            // vitesse en kilometre/seconde
            DecimalFormat df1 = new DecimalFormat("#.#");

            double totalsecondav = (double) totalseconde;
            double averagespeeder = (distance/totalsecondav)*3600;
            if(distance> 0.05) {
                MainActivity.averagespeed.setText(String.valueOf(df1.format(averagespeeder)));
            }
            lStart = lEnd;

            /////////////////////////////////////// REVOIR LA CONDITION//////////////////////

            ///////// CHECK CONDITION/////////////
            if ((int)distance == ((int)distanceafter)+1)
            {
                long timer = totalseconde - time0;
                time0 = totalseconde;

                double averagespeed = (3600/timer) ;
                storetaveragespeed.add(String.valueOf(df1.format(averagespeed)));


                System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

                //long time = totalseconde;
                int speedtoint = (int) timer;
                storetime.add(Integer.toString(speedtoint));
                storespeedandtime.put(Integer.toString(speedtoint),String.valueOf(df1.format(averagespeed)));

                distanceafter = distance;


            }
            DecimalFormat df0 = new DecimalFormat("#.#");
            distancereturn = df0.format(distance);

            ///////////////////////////////////////////////////////////////////////////////
        }

    }
    public static List<String> returnlisttime ()
    {
        return storetime;
    }

    public static List<String> returnlistaveragespeed ()
    {
        return storetaveragespeed;
    }

    public static String returndistance(){return distancereturn;}

    public static double returndistanceint(){return distance;}


    @Override
    public boolean onUnbind(Intent intent) {
        stopLocationUpdates();
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        lStart = null;
        lEnd = null;
        distance = 0;
        return super.onUnbind(intent);
    }
}