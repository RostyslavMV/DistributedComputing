package com.rmv.dc.lab3.a;

import lombok.Setter;

@Setter
public class BearRunnable implements Runnable {

    private Pot pot;
    private boolean wantsToEat = true;

    public BearRunnable(Pot pot) {
        this.pot = pot;
    }

    @Override
    public void run() {
        while (wantsToEat) {
            if(pot.isFull()){
                try {
                    System.out.println("Bear ate the honey.");
                    System.out.println(pot.getCurrentHoney().get());
                    pot.getCurrentHoney().set(0);
                    BeeRunnable.getSemaphore().releaseAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
