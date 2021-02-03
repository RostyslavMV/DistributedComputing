package com.rmv.dc.lab3.a;


import lombok.Getter;
import lombok.Setter;

@Setter
public class BeeRunnable implements Runnable {

    private static Pot pot;
    @Getter
    private static BoundedSemaphore semaphore;
    private boolean working = true;

    @Override
    public void run() {
        while (working) {
            try {
                semaphore.take();
                pot.getCurrentHoney().incrementAndGet();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setPot(Pot pot){
        BeeRunnable.pot = pot;
        semaphore = new BoundedSemaphore(pot.getHoneyCapacity());
    }
}