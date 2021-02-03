package com.rmv.dc.lab4.с;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TripGraph {

    private final MyReadWriteLock readWriteLock = new MyReadWriteLock();

    private final List<Integer> cities = new ArrayList<>();
    private final List<Trip> trips = new ArrayList<>();
}
