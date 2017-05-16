package com.example.michal.speedmeter;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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


public class MainActivity extends AppCompatActivity {

   // configuration parameters
   final long refreshTimeMs = 5000;
   final long refreshDistanceMeters = 0;

    private class VelocityFormat
    {
        public static final float KPH = 3.6f;
        public static final float MPH = 2.23693629f;
    }

    private class VelocityStringFormat
    {
        public static final String KPH = "km/h";
        public static final String MPH = "mph";
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

    final int locationIndicator = 10;

    TextView textView;

    LocationManager locationManager;
    LocationListener locationListener;

    private void updateSpeed(float velocityMps)
    {
        float speed;
        String format;

        if(true)    // TODO: add configuration option to choosee Kph or Mph
        {
            speed = convertSpeed(velocityMps, VelocityFormat.KPH);
            format = VelocityStringFormat.KPH;
        }

        else
        {
            speed = convertSpeed(velocityMps, VelocityFormat.MPH);
            format = VelocityStringFormat.MPH;
        }

        textView.setText(createVelocityString(speed, format));

    }

    private void showEnableGpsDialog()
    {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(this)
                        .setTitle("GPS is Settings")
                        .setMessage("GPS is not Enabled. Do you want to go to settings menu?")
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
            //showEnableGpsDialog();
            return;
        }

        locationManager.requestLocationUpdates("gps", refreshTimeMs, refreshDistanceMeters, locationListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);

        textView.setText(createVelocityString(VelocityStringFormat.KPH) );

        // the system service should be initialized, in constructor - type of service to request
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //after init system service initialize a location listener
        locationListener = new LocationListener() {
            // call whenever the location is changed
            @Override
            public void onLocationChanged(Location location)
            {
                updateSpeed(location.getSpeed());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            // check when the GPS is turned off
            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    showEnableGpsDialog();
                }
            }
        };
    }

    @Override
    protected void onStart()
    {
        super.onStart();
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

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            showEnableGpsDialog();
        }

        initializeLocation();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
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