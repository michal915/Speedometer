package com.example.michal.speedmeter.Printer;

import android.app.Activity;
import android.util.Pair;

import java.util.Observable;
import java.util.Observer;

public class PrinterVelocity extends Printer implements Observer
{
    public PrinterVelocity(Activity activity)
    {
        super(activity);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        Pair<Float, Float> velocityMessage = (Pair<Float, Float>)arg;

        printVelocity(velocityMessage.first);
        printMaxVelocity(velocityMessage.second);
    }
}
