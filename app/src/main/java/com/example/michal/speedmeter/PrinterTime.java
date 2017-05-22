package com.example.michal.speedmeter;

import android.app.Activity;

import java.util.Observable;
import java.util.Observer;

public class PrinterTime extends Printer implements Observer
{
    PrinterTime(Activity activity)
    {
        super(activity);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        printTime((long)arg);
    }
}