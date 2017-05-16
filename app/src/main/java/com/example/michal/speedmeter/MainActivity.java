package com.example.michal.speedmeter;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

   // configuration parameters
    final long refreshTimeMs = 1000;
    final long refreshDistanceMeters = 0;

    TextView textVelocityView;
    TextView textDistanceView;
    TextView textFullDistanceView;

    LocationManager locationManager;
    LocationListener locationListener;

    DistanceMonitor distanceMonitor;

    boolean isStarted = false;

    private class VelocityFormat
    {
        public static final float KPH = 3.6f;
        public static final float MPH = 2.23693629f;
    }

    private float convertSpeed(float speedMps, float format)
    {
        return (speedMps * format);
    }

    private SpannableString createVelocityString(float velocity, String format)
    {
        String velocityValue = String.format("%.1f", (velocity));
        String velocityInfo = velocityValue + " " + format;
        SpannableString message =  new SpannableString(velocityInfo);
        message.setSpan(new RelativeSizeSpan(5f), 0, velocityValue.length(), 0);

        return message;
    }

    private SpannableString createVelocityString(String format)
    {
        return createVelocityString(0.0f, format);
    }

    private void updateSpeed(float velocityMps)
    {
        float speed;
        String format;

        if(true)    // TODO: add configuration option to choosee Kph or Mph
        {
            speed = convertSpeed(velocityMps, VelocityFormat.KPH);
            format = getResources().getString(R.string.velocity_kmh);
        }

        else
        {
            speed = convertSpeed(velocityMps, VelocityFormat.MPH);
            format = getResources().getString(R.string.velocity_mph);
        }

        textVelocityView.setText(createVelocityString(speed, format));

    }

    private void showEnableGpsDialog(String tmpWhereIsCalled)
    {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(this)
                        .setTitle("GPS is Settings")
                        .setMessage("GPS is not Enabled. [" + tmpWhereIsCalled + "]?")
                                //"/*Do you want to go to settings menu*/ "

                        .setPositiveButton("Settings", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.show();
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
        int id = item.getItemId();
        if(id == R.id.menu_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            distanceMonitor = new DistanceMonitor(this, locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER));

            isStarted = true;
        }

        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void initializeTextView()
    {
        textVelocityView = (TextView) findViewById(R.id.text_velocity);
        textDistanceView = (TextView) findViewById(R.id.text_distance);
        textFullDistanceView = (TextView) findViewById(R.id.text_fullDistance);
        textVelocityView.setText(createVelocityString(0 + getResources().getString(R.string.velocity_kmh)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeTextView();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            showEnableGpsDialog("onCreate");
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location)
            {
                updateSpeed(location.getSpeed());

                if(isStarted) {
                    final String distance = String.format(Locale.US, "%.2f",
                            (distanceMonitor.updateDistance(location) * 0.001f)) +
                            getResources().getString(R.string.distance_km);
                    textDistanceView.setText(distance);
                }

                final String fullDistance = String.format(Locale.US, "%.1f",
                        (distanceMonitor.getFullDistance() * 0.001f)) +
                        getResources().getString(R.string.distance_km);
                textFullDistanceView.setText(fullDistance);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {

            }

            @Override
            public void onProviderEnabled(String provider)
            {

            }

            // check when the GPS is turned off
            @Override
            public void onProviderDisabled(String provider)
            {
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    showEnableGpsDialog("onProviderDisabled");
                }
            }
        };

        initializeLocation();
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
        distanceMonitor.saveFullDistance();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}