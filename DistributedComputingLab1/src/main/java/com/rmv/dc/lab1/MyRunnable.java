package com.rmv.dc.lab1;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class MyRunnable implements Runnable {

    private int targetNumber;
    private boolean running;

    private final JSlider slider;

    public MyRunnable(int targetNumber, JSlider slider) {
        this.targetNumber = targetNumber;
        this.slider = slider;
        this.running = true;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            moveSlider();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void moveSlider() {
        synchronized (slider) {
                slider.setValue(targetNumber);
        }
    }
}
