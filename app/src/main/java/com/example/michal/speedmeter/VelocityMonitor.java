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
        ConvertSpeed.mpsTokph(velocityMps);
        mVelocity = ConvertSpeed.mpsTokph(velocityMps);
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
