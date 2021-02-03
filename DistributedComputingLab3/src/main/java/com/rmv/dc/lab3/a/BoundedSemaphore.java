package com.rmv.dc.lab3.a;

public class BoundedSemaphore {
    private int signals = 0;
    private int bound = 0;

    public BoundedSemaphore(int upperBound) {
        this.bound = upperBound;
    }

    public synchronized void take() throws InterruptedException {
        while (this.signals == bound) wait();
        this.signals++;
        this.notify();
    }

    public synchronized void releaseAll() throws InterruptedException {
        while (this.signals == 0) wait();
        this.signals = 0;
        this.notify();
    }
}
