package com.rmv.dc.lab4.с;

import java.util.Random;

public class Application {

    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        TripGraph tripGraph = new TripGraph();
        for (int i = 0; i < 100; i++) {
            tripGraph.getCities().add(random.nextInt(150));
        }
        for (int i = 0; i < 10000; i++) {
            int cityA = tripGraph.getCities().get(random.nextInt(tripGraph.getCities().size()));
            int cityB = tripGraph.getCities().get(random.nextInt(tripGraph.getCities().size()));
            Trip trip = new Trip(cityA, cityB, random.nextInt(100));
            tripGraph.getTrips().add(trip);
        }
        Thread[] threads = new Thread[30];
        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(new AddCityRunnable(tripGraph, 100 * (i + 1)));
        }
        for (int i = 5; i < 10; i++) {
            int cityA = tripGraph.getCities().get(random.nextInt(tripGraph.getCities().size()));
            int cityB = tripGraph.getCities().get(random.nextInt(tripGraph.getCities().size()));
            threads[i] = new Thread(new AddTripRunnable(tripGraph, cityA, cityB, random.nextInt(100)));
        }
        for (int i = 10; i < 15; i++) {
            threads[i] = new Thread(
                    new CheckIfThereIsTripRunnable(tripGraph, random.nextInt(150), random.nextInt(150)));
        }
        for (int i = 15; i < 20; i++) {
            threads[i] = new Thread(new DeleteCityRunnable(tripGraph, random.nextInt(200)));
        }
        for (int i = 20; i < 25; i++) {
            threads[i] = new Thread(new DeleteTripRunnable(tripGraph, random.nextInt(200), random.nextInt(200)));
        }
        for (int i = 25; i < 30; i++) {
            threads[i] = new Thread(
                    new CheckIfThereIsTripRunnable(tripGraph, random.nextInt(200), random.nextInt(200)));
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
