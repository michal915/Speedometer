package com.example.michal.speedmeter;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;


public class DistanceMonitor
{
    private static String mPREFERENCE_NAME = "SpeedMeterPrefs";
    private Location      mActualLocation;
    private Location      mLastLocation;
    private float         mDistance; // meters
    private static float  mFullDistance;
    private Context       mContext;

    /**
     * DistanceMonitor constructor, initialize both locations data to
     * actual position, read the fullDistance from sharedPreference
     * and set the current distance value to 0
     *
     * @param context current context
     * @param location current location
     *
     */
    DistanceMonitor(Context context, Location location) {
        mActualLocation = location;
        mLastLocation = location;
        mDistance = 0.0f;
        mContext = context;
        mFullDistance = readFullDistance();
    }


    /**
     * Get the shared pereference object
     *
     * @param context current context
     *
     * @return shared preferences object
     *
     */
    private static SharedPreferences getSharedPreference(Context context)
    {
        return context.getSharedPreferences(mPREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Read the shared preference data
     *
     * @param context current context
     *
     * @return full distance since application installed
     *
     */
    private static float getSharedFullDistance(Context context)
    {
        return getSharedPreference(context).getFloat(Keys.fullDistance, 0.0f);
    }

    /**
     * Write the shared preference data
     *
     * @param context current context
     * @param input full distance value
     *
     */
    private static void setSharedFullDistance(Context context, float input)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putFloat(Keys.fullDistance, input);
        editor.commit();
    }

    /**
     * Set the distance value
     *
     * @param distance set new distance value  (meters)
     *
     */
    public void setDistance(float distance)
    {
        mDistance = distance;
    }

    /**
     * Reset distance value (set to zero)
     */
    public void resetDistance()
    {
        setDistance(0.0f);
    }

    /**
     * Update distance data when GPS is refreshed
     *
     * @param location new location
     *
     * @return return distance updated about new position
     *
     */
    public float updateDistance(Location location)
    {
        mActualLocation = location;
        final float distanceToLast = location.distanceTo(mLastLocation);
        mDistance += distanceToLast;
        mFullDistance += distanceToLast;
        mLastLocation = mActualLocation;

        return mDistance;
    }

    /**
     * Get the distance value
     *
     * @return get distance value  (meters)
     *
     */
    public float getDistance()
    {
        return mDistance;
    }

    /**
     * Read the full distance value, (the value was already read
     * form sharedPreferences in the constructor, this function
     * not read the value from sharedPreferences, but from static
     * data inside the class
     *
     * @return full distance value (meters)
     *
     */
    public float getFullDistance()
    {
        return mFullDistance;
    }

    /**
     * Write the full distance value, (the value was already read
     * form sharedPreferences in the constructor, this function
     * not write the value into sharedPreferences, but from static
     * data inside the class, use writeFullDistance() or
     * readFullDistance instead
     *
     * @return full distance value (meters)
     *
     */
    public void setFullDistance(float fullDistance)
    {
        mFullDistance = fullDistance;
    }

    /**
     * Write the full distance value into sharedPreference
     *
     * @param input full distance value to be write (meters)
     *
     */
    public void saveFullDistance(float input)
    {
        mFullDistance = input;
        setSharedFullDistance(mContext, mFullDistance);
    }

    /**
     * Write the full distance value into sharedPreference
     *
     */
    public void saveFullDistance()
    {
        setSharedFullDistance(mContext, mFullDistance);
    }

    /**
     * Write the full distance value into sharedPreference
     *
     * @return fullDistance value from sharedPreference (meters)
     *
     */
    public float readFullDistance()
    {
        return getSharedFullDistance(mContext);
    }
};
