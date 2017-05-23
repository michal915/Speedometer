package com.example.michal.speedmeter.Conversions;

public final class ConvertDistance {

    /**
     * Constructor, private we don't want to create this object
     */
    private ConvertDistance()
    {

    }

    /**
     * Meters to Miles
     * @param m distance meters
     * @return distance miles
     */
    public static float mTomil(float m)
    {
        return m* 0.000621371f;
    }

    /**
     * Kilometers to Miles
     * @param km distance kilometers
     * @return distance miles
     */
    public static float kmTomil(float km)
    {
        return km * 0.621371f;
    }


    /**
     * Miles to kilometers
     * @param mil distance miles
     * @return distance kilometers
     */
    public static float milTokm(float mil)
    {
        return mil * 1.60934f;
    }

    /**
     * Miles to meters
     * @param mil distance miles
     * @return distance meters
     */
    public static float milTom(float mil)
    {
        return mil * 0.621371f;
    }

}
