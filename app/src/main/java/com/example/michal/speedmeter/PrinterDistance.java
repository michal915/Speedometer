package com.example.michal.speedmeter;

import android.app.Activity;
import android.util.Pair;

import java.util.Observable;
import java.util.Observer;

public class PrinterDistance extends Printer implements Observer
{
    PrinterDistance(Activity activity)
    {
        super(activity);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        Pair<Float, Float> distanceMessage = (Pair<Float, Float>)arg;

        printDistance(distanceMessage.first);
        printTotalDistance(distanceMessage.second);
    }
}

