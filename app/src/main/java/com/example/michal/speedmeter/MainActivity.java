package com.example.michal.speedmeter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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


public class MainActivity extends AppCompatActivity {

   // configuration parameters
   // final long refreshTimeMs = 5000;
   // final long refreshDistanceMeters = 0;

    private class VelocityFormat
    {
        public static final float KPH = 3.6f;
        public static final float MPH = 2.23693629f;
    }

    private float convertSpeed(float speedMps, float format)
    {
        return (speedMps * format);
    }

    private SpannableString getVelocityString(float velocity, String format)
    {
        String velocityValue = String.format("%.1f", (velocity));
        String velocityInfo = velocityValue + " " + format;
        SpannableString message =  new SpannableString(velocityInfo);
        message.setSpan(new RelativeSizeSpan(5f), 0, velocityValue.length(), 0);

        return message;
    }

    final int indicator = 10;

    TextView textView;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);

        textView.setText(getVelocityString(0.00f, "km/h") );

        // the system service should be initialized, in constructor - type of service to request
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //after init system service initialize a location listener
        locationListener = new LocationListener() {
            // call whenever the location is changed
            @Override
            public void onLocationChanged(Location location) {

                float speed;
                String format;

                if(true)    // TODO: add configuration option to choosee Kph or Mph
                {
                    speed = convertSpeed(location.getSpeed(), VelocityFormat.KPH);
                    format = "km/h";
                }

                else
                {
                    speed = convertSpeed(location.getSpeed(), VelocityFormat.MPH);
                    format = "mph";
                }

                textView.setText(getVelocityString(speed, format));
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
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }

        else
        {
            initializeLocation();
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
        int id = item.getItemId();
        if(id == R.id.menu_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case indicator:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    initializeLocation();
                return;
        }
    }

    private void initializeLocation()
    {
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }
}