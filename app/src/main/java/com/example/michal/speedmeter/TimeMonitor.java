package com.example.michal.speedmeter;

import android.os.Build;

/**
 * Created by michal on 5/18/17.
 */

public class TimeMonitor {
    private long mActualTime;
    private long mLastTime;
    private long mElapsedTime;

    TimeMonitor(long time)
    {
        mActualTime = time;
        mLastTime = time;
        mElapsedTime = 0;
    }

    public void setActualTime(long time)
    {
        mActualTime = time;
    }

    public void setLastTime(long time)
    {
        mLastTime = time;
    }

    public void setElapsedTime(long time)
    {
        mElapsedTime = time;
    }

    public void updateElapsedTime(long actualTime)
    {
        mActualTime = actualTime;
        mElapsedTime += (mActualTime - mLastTime);
        mLastTime = mActualTime;
    }

    public void updateTime(long actualTime)
    {
        mActualTime = actualTime;
        mLastTime = mActualTime;
    }

    public long getActualTime()
    {
        return mActualTime;
    }

    public long getLastTime()
    {
        return mLastTime;
    }

    public long getElapsedTime()
    {
        return mElapsedTime;
    }
}
