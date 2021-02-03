package com.rmv.dc.lab4.с;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteTripRunnable implements Runnable {

    private final TripGraph tripGraph;
    private final int a;
    private final int b;

    @Override
    public void run() {
        deleteTrip(a, b);
    }

    private void deleteTrip(int a, int b) {
        try {
            tripGraph.getReadWriteLock().writeLock();
            for (Trip trip : tripGraph.getTrips()) {
                if (trip.getA() == a) {
                    if (trip.getB() == b){
                        tripGraph.getTrips().remove(trip);
                        tripGraph.getReadWriteLock().writeUnlock();
                        return;
                    }
                }
                if (trip.getB() == a) {
                    if (trip.getA() == b) {
                        tripGraph.getTrips().remove(trip);
                        tripGraph.getReadWriteLock().writeUnlock();
                        return;
                    }
                }
            }
            tripGraph.getReadWriteLock().writeUnlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
