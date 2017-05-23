package com.example.michal.speedmeter.Printer;

import android.app.Activity;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.example.michal.speedmeter.R;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

/**
 * Printer is using to print the data into textView boxes
 * and other gui elements
 */
public class Printer
{
    TextView mTextVelocityView;
    private TextView mTextDistanceView;
    private TextView mTextFullDistanceView;
    private TextView mTextTimeView;
    private TextView mTextMaxVelocityView;
    private Activity mActivity;

    /**
     * Constructor initialize the gui objects using activity from main
     * @param activity
     */
    public Printer(Activity activity)
    {
        mActivity = activity;
        mTextVelocityView     = (TextView) mActivity.findViewById(R.id.text_vel_value);
        mTextDistanceView     = (TextView) mActivity.findViewById(R.id.text_distance_value);
        mTextFullDistanceView = (TextView) mActivity.findViewById(R.id.text_full_distance_value);
        mTextTimeView         = (TextView) mActivity.findViewById(R.id.text_time_value);
        mTextMaxVelocityView  = (TextView) mActivity.findViewById(R.id.text_max_velocity_value);
        mTextVelocityView.setText(createVelocityString(0 + mActivity.getResources().getString(R.string.velocity_kmh)));
    }

    /**
     * Create the SpannableString with velocity value
     * @param velocity velocity km/h
     * @param format metric system km/h, mps, ms
     * @return string to show
     */
    private SpannableString createVelocityString(float velocity, String format)
    {
        String velocityValue = String.format(Locale.US, "%.1f", (velocity));
        String velocityInfo = velocityValue + " " + format;
        SpannableString message =  new SpannableString(velocityInfo);
        message.setSpan(new RelativeSizeSpan(3f), 0, velocityValue.length(), 0);

        return message;
    }

    /**
     * Create the SpannableString with velocity input value equal to 0 km/h
     * @param format metric system km/h, mps, ms
     * @return string to show
     */
    private SpannableString createVelocityString(String format)
    {
        return createVelocityString(0.0f, format);
    }

    /**
     * Round value, using mathematical concept
     * @param number number to round
     * @param decimalPlace precision
     * @return rounded value
     */
    private float round(float number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(number));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /**
     * Print actual velocity
     * @param velocity in km/h
     */
    public void printVelocity(float velocity)
    {
        mTextVelocityView.setText(createVelocityString(velocity, "km/h"));
    }

    /**
     * Print value of maximal velocity
     * @param velocity
     */
    public void printMaxVelocity(float velocity)
    {
        velocity = round(velocity, 1);
        String speedText = String.format(Locale.US, "%.1f", velocity);
        mTextMaxVelocityView.setText(speedText + " km/h");
    }

    /**
     * Print distance
     * @param distance distance in meters
     */
    public void printDistance(float distance)
    {
        final String distanceText = String.format(Locale.US, "%.1f",
                round((distance * 0.001f), 1)) + " " + mActivity.getResources().getString(R.string.distance_km);
        mTextDistanceView.setText(distanceText);
    }

    /**
     * Print distance
     * @param totalDistance total distance in meters
     */
    public void printTotalDistance(float totalDistance)
    {
        final long totalDistanceValue = (long)round((totalDistance * 0.001f),0);

        final String totalDistanceText = String.format(Locale.US, "%d", totalDistanceValue)
                + " " + mActivity.getResources().getString(R.string.distance_km);

        mTextFullDistanceView.setText(totalDistanceText);
    }

    /**
     * Print time in format hh:mm:ss:
     * @param mills linux epoch time in millisecond
     */
    public void printTime(long mills)
    {
        final long hrs = (mills/(1000 * 60 * 60));
        final long min = (mills/(1000*60)) % 60;
        final long sec = (mills/1000) % 60;

        final String hh = String.format(Locale.US, "%02d", hrs);
        final String mm = String.format(Locale.US, "%02d", min);
        final String ss = String.format(Locale.US, "%02d", sec);

        final String timeText = (hh + ":" + mm + ":" + ss);

        mTextTimeView.setText(timeText);
    }
}
