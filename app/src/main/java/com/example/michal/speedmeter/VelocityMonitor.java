package com.example.michal.speedmeter;

import android.content.Context;
import android.content.SharedPreferences;

public class VelocityMonitor {

    private float   mVelocity;    // km/h
    private float   mMaxVelocity; // km/h
    private Context mContext;


    VelocityMonitor(Context context, float velocity)
    {
        mContext = context;
        mVelocity = velocity;
        mMaxVelocity = getSharedMaxVelocity(context);
    }

    VelocityMonitor(Context context)
    {
        mContext = context;
        mVelocity = 0;
        mMaxVelocity = getSharedMaxVelocity(context);
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
        return context.getSharedPreferences(Keys.preferenceName, Context.MODE_PRIVATE);
    }

    /**
     * Read the shared preference data
     *
     * @param context current context
     *
     * @return max velocity since application installed
     *
     */
    private static float getSharedMaxVelocity(Context context)
    {
        return getSharedPreference(context).getFloat(Keys.maxVelocity, 0.0f);
    }

    /**
     * Write the shared preference data
     *
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

    public void updateVelocity(float velocityMps)
    {
        ConvertSpeed.mpsTokph(velocityMps);
        mVelocity = ConvertSpeed.mpsTokph(velocityMps);
        if(mVelocity > getMaxVelocity())
        {
            writeMaxVelocity(mVelocity);
        }
    }

    public float getVelocity()
    {
        return mVelocity;
    }

    public void setVelocity(float velocity)
    {
        mVelocity = velocity;
    }

    public void writeMaxVelocity(float velocity)
    {
        mMaxVelocity = velocity;
        setSharedMaxVelocity(mContext, mMaxVelocity);
    }

    public float getMaxVelocity()
    {
        return mMaxVelocity;
    }

    public float readMaxVelocity()
    {
        return getSharedMaxVelocity(mContext);
    }
}
