package com.example.michal.speedmeter;

public class ConvertSpeed {
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
     * Kilometers per hour to Miles per hours
     *
     * @param kph velocity km/h
     *
     * @return velocity mph
     */
    public static float kphTomph(float kph)
    {
        return kph * 0.621371f;
    }


    /**
     * Miles per hours to Kilometers per hour
     *
     * @param mph velocity mph
     *
     * @return velocity km/h
     */
    public static float mphTokph(float mph)
    {
        return mph * 1.60934f;
    }
}
