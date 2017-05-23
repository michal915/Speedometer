package com.example.michal.speedmeter.Printer;

import android.app.Activity;
import android.util.Pair;

import java.util.Observable;
import java.util.Observer;

public class PrinterDistance extends Printer implements Observer
{
    public PrinterDistance(Activity activity)
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

