package com.rmv.dc.lab3.b;

public class Application {

    public static void main(String[] args) {
        int visitorsNumber = 5;
        Thread[] threads = new Thread[visitorsNumber];

        Barber barber = new Barber();

        for (int i = 0; i < visitorsNumber; i++) {
            threads[i] = new Thread(new VisitorRunnable(barber), "Visitor " + i);
            threads[i].start();
        }

        for (int i =0; i<visitorsNumber; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
