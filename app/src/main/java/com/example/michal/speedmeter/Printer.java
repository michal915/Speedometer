package com.example.michal.speedmeter;

import android.app.Activity;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Locale;

public class Printer {
    private TextView mTextVelocityView;
    private TextView mTextDistanceView;
    private TextView mTextFullDistanceView;
    private TextView mTextTimeView;
    private TextView mTextMaxVelocityView;
    private Activity mActivity;

    Printer(Activity activity)
    {
        mActivity = activity;
        mTextVelocityView     = (TextView) mActivity.findViewById(R.id.text_vel_value);
        mTextDistanceView     = (TextView) mActivity.findViewById(R.id.text_distance_value);
        mTextFullDistanceView = (TextView) mActivity.findViewById(R.id.text_full_distance_value);
        mTextTimeView         = (TextView) mActivity.findViewById(R.id.text_time_value);
        mTextMaxVelocityView  = (TextView) mActivity.findViewById(R.id.text_max_velocity_value);
        mTextVelocityView.setText(createVelocityString(0 + mActivity.getResources().getString(R.string.velocity_kmh)));
    }

    private SpannableString createVelocityString(float velocity, String format)
    {
        String velocityValue = String.format(Locale.US, "%.1f", (velocity));
        String velocityInfo = velocityValue + " " + format;
        SpannableString message =  new SpannableString(velocityInfo);
        message.setSpan(new RelativeSizeSpan(3f), 0, velocityValue.length(), 0);

        return message;
    }

    private SpannableString createVelocityString(String format)
    {
        return createVelocityString(0.0f, format);
    }

    private static float round(float number, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(number));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public void printVelocity(float speed)
    {
        mTextVelocityView.setText(createVelocityString(speed, "km/h"));
    }

    public void printMaxVelocity(float speed)
    {
        speed = round(speed, 1);
        String speedText = String.format(Locale.US, "%.1f", speed);
        mTextMaxVelocityView.setText(speedText + " km/h");
    }

    public void printDistance(float distance)
    {
        final String distanceText = String.format(Locale.US, "%.1f",
                round((distance * 0.001f), 1)) + " " + mActivity.getResources().getString(R.string.distance_km);
        mTextDistanceView.setText(distanceText);
    }

    public void printTotalDistance(float totalDistance)
    {
        final long totalDistanceValue = (long)round((totalDistance * 0.001f),0);

        final String totalDistanceText = String.format(Locale.US, "%d", totalDistanceValue)
                + " " + mActivity.getResources().getString(R.string.distance_km);

        mTextFullDistanceView.setText(totalDistanceText);
    }

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
