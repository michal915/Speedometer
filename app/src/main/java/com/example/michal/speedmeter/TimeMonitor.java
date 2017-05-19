package com.example.michal.speedmeter;

/**
 * TimeMonitor monitoring the time using GPS time since application
 * running, incremented only when device hasSpeed
 */
public class TimeMonitor {
    private long mActualTime;
    private long mLastTime;
    private long mElapsedTime;

    /**
     * Constructor initialize last and actual time
     * the first difference should be 0 to increment
     * correct values
     * @param time linux epoch time in millisecond
     */
    TimeMonitor(long time)
    {
        mActualTime = time;
        mLastTime = time;
        mElapsedTime = 0;
    }

    /**
     * Setting actual time
     * @param time linux epoch time in millisecond
     */
    public void setActualTime(long time)
    {
        mActualTime = time;
    }

    /**
     * Setting last time
     * @param time linux epoch time in millisecond
     */
    public void setLastTime(long time)
    {
        mLastTime = time;
    }

    /**
     * Setting time since application running, used when
     * restore data
     * @param time linux epoch time in millisecond
     */
    public void setElapsedTime(long time)
    {
        mElapsedTime = time;
    }

    /**
     * Update, increment time value
     * @param actualTime linux epoch time in millisecond
     */
    public void updateElapsedTime(long actualTime)
    {
        mActualTime = actualTime;
        mElapsedTime += (mActualTime - mLastTime);
        mLastTime = mActualTime;
    }

    /**
     * Update, increment last/actual data, mElapsedTime
     * not incremented, using when speed is 0 km/h
     * @param actualTime
     */
    public void updateTime(long actualTime)
    {
        mActualTime = actualTime;
        mLastTime = mActualTime;
    }

    /**
     * Return actual time in epoch format
     * @return linux epoch time in millisecond
     */
    public long getActualTime()
    {
        return mActualTime;
    }

    /**
     * Return last time in epoch format
     * @return linux epoch time in millisecond
     */
    public long getLastTime()
    {
        return mLastTime;
    }

    /**
     * Return elapsed time in epoch format
     * @return linux epoch time in millisecond
     */
    public long getElapsedTime()
    {
        return mElapsedTime;
    }
}
