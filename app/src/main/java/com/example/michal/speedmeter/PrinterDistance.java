package com.example.michal.speedmeter;

import android.app.Activity;
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
        printVelocity((float)arg);
    }
}

