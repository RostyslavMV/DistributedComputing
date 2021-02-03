package com.rmv.dc.lab4.с;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddTripRunnable implements Runnable {

    private final TripGraph tripGraph;
    private final int a;
    private final int b;
    private final int price;

    @Override
    public void run() {

    }

    private void addTrip(int a, int b) {
        try {
            tripGraph.getReadWriteLock().writeLock();
            tripGraph.getTrips().add(new Trip(a, b, price));
            tripGraph.getReadWriteLock().writeUnlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
