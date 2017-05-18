package com.example.michal.speedmeter;

public class VelocityMonitor {

    private float mVelocity;    // km/h

    VelocityMonitor()
    {
        mVelocity = 0;
    }

    VelocityMonitor(float velocity)
    {
        mVelocity = velocity;
    }

    public void updateVelocity(float velocityMps)
    {
        Convert.mpsTokph(velocityMps);
        mVelocity = Convert.mpsTokph(velocityMps);
    }

    public float getVelocity()
    {
        return mVelocity;
    }

    public void setVelocity(float velocity)
    {
        mVelocity = velocity;
    }
}
