package com.example.michal.speedmeter;

public class Convert {

    /**
     * Meter per second to Kilometers per hours
     *
     * @param mps velocity m/s
     *
     * @return velocity km/h
     */
    public static float mpsTokph(float mps)
    {
        return mps * 3.6f;
    }

    /**
     * Meter per second to Miles per hours
     *
     * @param mps velocity m/s
     *
     * @return velocity mph
     */
    public static float mpsTomph(float mps)
    {
        return mps * 2.23693629f;
    }

    /**
     * Meters to Miles
     *
     * @param m distance meters
     *
     * @return distance miles
     */
    public static float mTomil(float m)
    {
        return -1;
    }

    /**
     * Kilometers to Miles
     *
     * @param km distance kilometers
     *
     * @return distance miles
     */
    public static float kmTomil(float km)
    {
        return -1;
    }
}
