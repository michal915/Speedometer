package com.example.michal.speedmeter;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Pair;

import java.util.Observable;

/**
 * DistanceMonitor monitoring the values of
 * actual distance and total distance since the
 * application was installed all values is
 * represented in meters.
 */
public class DistanceMonitor  extends Observable
{
    private Location      mActualLocation;
    private Location      mLastLocation;
    private float         mDistance;
    private float         mTotalDistance;
    private Context       mContext;

    /**
     * DistanceMonitor constructor, initialize both locations data to
     * actual position, read the Total Distance from sharedPreference
     * and set the current distance value to 0.
     * @param context current context
     * @param location current location
     *
     */
    DistanceMonitor(Context context, Location location) {
        mActualLocation = location;
        mLastLocation = location;
        mDistance = 0.0f;
        mContext = context;
        mTotalDistance = readTotalDistance();
    }

    /**
     * Get the shared preference object.
     * @param context current context
     * @return shared preferences object
     */
    private static SharedPreferences getSharedPreference(Context context)
    {
        return context.getSharedPreferences(Keys.preferenceName, Context.MODE_PRIVATE);
    }

    /**
     * Read the shared preference data.
     * @param context current context
     * @return total distance since application installed
     */
    private static float getSharedTotalDistance(Context context)
    {
        return getSharedPreference(context).getFloat(Keys.totalDistance, 0.0f);
    }

    /**
     * Write the shared preference data.
     * @param context current context
     * @param input total distance value (meters)
     */
    private static void setSharedTotalDistance(Context context, float input)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putFloat(Keys.totalDistance, input);
        editor.apply();
    }

    /**
     * Set the distance value.
     * @param distance set new distance value (meters)
     */
    public void setDistance(float distance)
    {
        mDistance = distance;
        sendMessage();
    }

    /**
     * Reset distance value (set to zero)
     */
    public void resetDistance()
    {
        setDistance(0.0f);
    }

    /**
     * Update distance data when GPS is refreshed.
     * @param location new location
     */
    public void updateDistance(Location location)
    {
        mActualLocation = location;
        final float distanceToLast = location.distanceTo(mLastLocation);
        mDistance += distanceToLast;
        mTotalDistance += distanceToLast;
        mLastLocation = mActualLocation;

        sendMessage();
    }

    /**
     * Get the distance value.
     * @return get distance value  (meters)
     */
    public float getDistance()
    {
        return mDistance;
    }

    /**
     * Read the total distance value, (the value was already read
     * form sharedPreferences in the constructor, this function
     * not read the value from sharedPreferences, but from static
     * data inside the class.
     * @return total distance value (meters)
     */
    public float getTotalDistance()
    {
        return mTotalDistance;
    }

    /**
     * Write the total distance value, (the value was already read
     * form sharedPreferences in the constructor, this function
     * not write the value into sharedPreferences, but from static
     * data inside the class, use writeTotalDistance() or
     * readTotalDistance instead.
     * @param totalDistance total distance value (meters)
     */
    public void setTotalDistance(float totalDistance)
    {
        mTotalDistance = totalDistance;
    }

    /**
     * Write the total distance value into sharedPreference.
     * @param input total distance value to be write (meters)
     */
    public void writeTotalDistance(float input)
    {
        mTotalDistance = input;
        setSharedTotalDistance(mContext, mTotalDistance);
    }

    /**
     * Write the total distance value into sharedPreference.
     */
    public void writeTotalDistance()
    {
        setSharedTotalDistance(mContext, mTotalDistance);
    }

    /**
     * Write the total distance value into sharedPreference.
     * @return total value from sharedPreference (meters)
     */
    public float readTotalDistance()
    {
        return getSharedTotalDistance(mContext);
    }

    /**
     * Send data to observers
     */
    private void sendMessage()
    {
        Pair<Float, Float> distanceMessage = Pair.create(getDistance(), getTotalDistance());
        setChanged();
        notifyObservers(distanceMessage);
    }
}
