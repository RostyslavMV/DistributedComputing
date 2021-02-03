package com.rmv.dc.lab2.a;

public class Application {

    public static void main(String[] args) {
        BeeRunnable.setForestTasksManager(new ForestTasksManager(100,100));
        int threadsNumber = 4;
        Thread[] threads = new Thread[4];
        for (int i = 0; i < threadsNumber; i++) {
           threads[i] = new Thread(new BeeRunnable());
           threads[i].start();
        }
        for (int i = 0; i < threadsNumber; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("X: " + BeeRunnable.getBearsX() + " Y: " + BeeRunnable.getBearsY());
    }
}
