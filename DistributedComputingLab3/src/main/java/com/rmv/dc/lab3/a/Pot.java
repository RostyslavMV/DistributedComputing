package com.rmv.dc.lab3.a;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Pot {
    private final int honeyCapacity = 100;
    private final AtomicInteger currentHoney = new AtomicInteger(0);

    public synchronized boolean isFull() {
        return currentHoney.get() == honeyCapacity;
    }
}
