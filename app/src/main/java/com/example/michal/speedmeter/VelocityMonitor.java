package com.example.michal.speedmeter;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * VelocityMonitor monitoring the changes of
 * actual velocity and maximal speed since the
 * application was installed all values is
 * represented in km/h.
 */
public class VelocityMonitor {

    private float   mVelocity;
    private float   mMaxVelocity;
    private Context mContext;

    /**
     * Constructor initialize actual context.
     * and velocity
     * @param context actual context
     * @param velocity velocity in km/h
     */
    VelocityMonitor(Context context, float velocity)
    {
        mContext = context;
        mVelocity = velocity;
        mMaxVelocity = getSharedMaxVelocity(context);
    }

    /**
     * Constructor, velocity is setup to zero.
     * @param context
     */
    VelocityMonitor(Context context)
    {
        mContext = context;
        mVelocity = 0;
        mMaxVelocity = getSharedMaxVelocity(context);
    }

    /**
     * Get the shared preference object.
     * @param context current context
     * @return shared preferences object
     *
     */
    private static SharedPreferences getSharedPreference(Context context)
    {
        return context.getSharedPreferences(Keys.preferenceName, Context.MODE_PRIVATE);
    }

    /**
     * Read the shared preference data.
     * @param context current context
     * @return max velocity since application installed
     *
     */
    private static float getSharedMaxVelocity(Context context)
    {
        return getSharedPreference(context).getFloat(Keys.maxVelocity, 0.0f);
    }

    /**
     * Write the shared preference data.
     * @param context current context
     * @param input max velocity
     *
     */
    private static void setSharedMaxVelocity(Context context, float input)
    {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putFloat(Keys.maxVelocity, input);
        editor.apply();
    }

    /**
     * Update velocity and max velocity value.
     * @param velocityMps
     */
    public void updateVelocity(float velocityMps)
    {
        ConvertSpeed.mpsTokph(velocityMps);
        mVelocity = ConvertSpeed.mpsTokph(velocityMps);
        if(mVelocity > getMaxVelocity())
        {
            writeMaxVelocity(mVelocity);
        }
    }

    /**
     * Get velocity value.
     * @return velocity km/h
     */
    public float getVelocity()
    {
        return mVelocity;
    }

    /**
     * Set velocity value.
     * @param velocity km/h
     */
    public void setVelocity(float velocity)
    {
        mVelocity = velocity;
    }

    public void writeMaxVelocity(float velocity)
    {
        mMaxVelocity = velocity;
        setSharedMaxVelocity(mContext, mMaxVelocity);
    }

    /**
     * Get maximal velocity since application
     * was installed, can be reset in reset option in settings.
     * @return maximal velocity km/h
     */
    public float getMaxVelocity()
    {
        return mMaxVelocity;
    }

    /**
     * Read the maximal velocity from
     * shared preferences.
     * @return maximal velocity from shared preferences
     */
    public float readMaxVelocity()
    {
        return getSharedMaxVelocity(mContext);
    }
}
