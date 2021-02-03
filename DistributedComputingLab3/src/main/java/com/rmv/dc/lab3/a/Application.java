package com.rmv.dc.lab3.a;

public class Application {

    public static void main(String[] args) {
        Pot pot = new Pot();
        Thread bearThread = new Thread(new BearRunnable(pot));
        bearThread.start();
        BeeRunnable.setPot(pot);
        for(int i =0;i<4;i++){
            Thread beeThread = new Thread(new BeeRunnable());
            beeThread.start();
        }
    }

}
