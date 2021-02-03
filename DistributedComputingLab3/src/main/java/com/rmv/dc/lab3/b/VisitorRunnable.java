package com.rmv.dc.lab3.b;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VisitorRunnable implements Runnable {

    private final Barber barber;

    @Override
    public void run() {
        barber.addVisitorToQueue(this);
    }
}
