package com.rmv.dc.lab4.с;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PriceChangerRunnable implements Runnable {

    private final TripGraph tripGraph;
    private final int priceMultiplier;

    @Override
    public void run() {
        changePrice(priceMultiplier);
    }

    private void changePrice(int priceMultiplier) {
        try {
            tripGraph.getReadWriteLock().writeLock();
            for (Trip trip : tripGraph.getTrips()) {
                trip.setPrice(trip.getPrice() * priceMultiplier);
            }
            tripGraph.getReadWriteLock().writeUnlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
