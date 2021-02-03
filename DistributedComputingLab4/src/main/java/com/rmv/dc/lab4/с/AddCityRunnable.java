package com.rmv.dc.lab4.с;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddCityRunnable implements Runnable{
    private final TripGraph tripGraph;
    private final int city;

    @Override
    public void run() {
        addCity(city);
    }

    private void addCity(int city){
        try {
            tripGraph.getReadWriteLock().readLock();
            if (tripGraph.getCities().contains(city)){
                tripGraph.getReadWriteLock().readUnlock();
                return;
            }
            tripGraph.getReadWriteLock().readUnlock();
            tripGraph.getReadWriteLock().writeLock();
            tripGraph.getCities().add(city);
            tripGraph.getReadWriteLock().writeUnlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
