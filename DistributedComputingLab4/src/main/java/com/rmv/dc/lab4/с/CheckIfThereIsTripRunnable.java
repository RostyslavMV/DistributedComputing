package com.rmv.dc.lab4.с;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CheckIfThereIsTripRunnable implements Runnable {
    private final TripGraph tripGraph;
    private final int a;
    private final int b;

    @Override
    public void run() {
        checkIfThereIsTrip(a, b);
    }

    private void checkIfThereIsTrip(int a, int b) {
        try {
            tripGraph.getReadWriteLock().readLock();
            if (!tripGraph.getCities().contains(a)) {
                System.out.println("There can't be trip between cities: " + a
                        + " and " + b + ", because " + a + " isn't present in the cities list");
                tripGraph.getReadWriteLock().readUnlock();
                return;
            }
            if (!tripGraph.getCities().contains(b)) {
                System.out.println("There can't be trip between cities: " + a
                        + " and " + b + ", because " + b + " isn't present in the cities list");
                tripGraph.getReadWriteLock().readUnlock();
                return;
            }

            for (Trip trip : tripGraph.getTrips()) {
                if (trip.getA() == a) {
                    if (trip.getB() == b) {
                        System.out.println("There is a trip between cities: " + a
                                + " and " + b + ", price is: " + trip.getPrice());
                        tripGraph.getReadWriteLock().readUnlock();
                        return;
                    }
                }
                if (trip.getB() == a) {
                    if (trip.getA() == b) {
                        System.out.println("There is a trip between cities: " + a
                                + " and " + b + ", price is: " + trip.getPrice());
                        tripGraph.getReadWriteLock().readUnlock();
                        return;
                    }
                }
            }
            System.out.println("There isn't trip between cities: " + a + " and " + b);
            tripGraph.getReadWriteLock().readUnlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
