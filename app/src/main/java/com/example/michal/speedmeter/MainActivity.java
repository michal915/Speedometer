package com.example.michal.speedmeter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

   // configuration parameters
    final long refreshTimeMs = 500;
    final long refreshDistanceMeters = 0;

    LocationManager  locationManager;
    LocationListener locationListener;

    DistanceMonitor  distanceMonitor;
    TimeMonitor      timeMonitor;
    VelocityMonitor  velocityMonitor;

    boolean isStarted = false;

    private void showEnableGpsDialog()
    {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.gps_dialog_title))
                        .setMessage(getResources().getString(R.string.gps_dialog_message))

                        .setPositiveButton(getResources().getString(R.string.gps_dialog_button_positive),
                                new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        }).setNegativeButton(getResources().getString(R.string.gps_dialog_button_negative),
                        new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

                alertDialogBuilder.show();
    }

    private void initializeLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Please enable Location in settings.", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            locationManager.requestLocationUpdates("gps", refreshTimeMs, refreshDistanceMeters, locationListener);
            final Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

            distanceMonitor = new DistanceMonitor(this, location);
            distanceMonitor.addObserver(new PrinterDistance(this));

            timeMonitor = new TimeMonitor(location.getTime());
            timeMonitor.addObserver(new PrinterTime(this));
            isStarted = true;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        final int id = item.getItemId();

        switch(id)
        {
            case R.id.menu_settings:
                break;

            case R.id.menu_reset:
                distanceMonitor.resetDistance();
                distanceMonitor.writeTotalDistance(0);
                timeMonitor.setElapsedTime(0);
                velocityMonitor.writeMaxVelocity(0.0f);
                break;
            default:
                return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if(distanceMonitor != null) {
            outState.putFloat(Keys.distance, distanceMonitor.getDistance());
        }
        if(timeMonitor != null) {
            outState.putLong(Keys.time, timeMonitor.getElapsedTime());
        }

        outState.putFloat(Keys.velocity, velocityMonitor.getVelocity());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        distanceMonitor.setDistance(savedInstanceState.getFloat(Keys.distance));
        timeMonitor.setElapsedTime(savedInstanceState.getLong(Keys.time));
        velocityMonitor.setVelocity(savedInstanceState.getFloat(Keys.velocity));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        velocityMonitor = new VelocityMonitor(this);
        velocityMonitor.addObserver(new PrinterVelocity(this));

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            showEnableGpsDialog();
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location)
            {
                // TODO: if gps paused, or no speed clear a speed data temporary ignore it, back when real GPS tests will be done
                if(isStarted)
                {
                    distanceMonitor.updateDistance(location);

                    if(location.hasSpeed())
                    {
                        timeMonitor.updateElapsedTime(location.getTime());
                        velocityMonitor.updateVelocity(location.getSpeed());
                    }
                }

                else
                {
                    velocityMonitor.updateVelocity(0);
                    timeMonitor.updateTime(location.getTime());
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {

            }

            @Override
            public void onProviderEnabled(String provider)
            {

            }

            @Override
            public void onProviderDisabled(String provider)
            {
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    showEnableGpsDialog();
                }
            }
        };

        initializeLocation();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

//        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
//        {
//            showEnableGpsDialog("onResume");
//        }
//
//        initializeLocation();

    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

//        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
//        {
//            showEnableGpsDialog("onResume");
//        }
//
//        initializeLocation();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        distanceMonitor.writeTotalDistance();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        distanceMonitor.writeTotalDistance();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}