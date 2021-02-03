package com.rmv.dc.lab2.c;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Application {

    public static void main(String[] args) {
        int monksCount = 100;
        int[] monksQiPower = new int[monksCount];
        for (int i = 0; i < monksCount; i++) {
            monksQiPower[i] = i;
        }

        Random random = new Random();
        for (int i = 0; i < monksCount; i++) {
            int index1 = random.nextInt(100);
            int index2 = random.nextInt(100);
            int temp = monksQiPower[index1];
            monksQiPower[index1] = monksQiPower[index2];
            monksQiPower[index2] = temp;
        }

        int expectedWinnerIndex = 0;
        int maxQiEnergy = 0;
        for (int i = 0; i < monksCount; i++) {
            if (monksQiPower[i] > maxQiEnergy) {
                expectedWinnerIndex = i;
                maxQiEnergy = monksQiPower[i];
            }
        }

        Competition.setMonksQiPower(monksQiPower);
        Competition competition = new Competition(0, monksQiPower.length-1);

            int winnerIndex = competition.compute();
            if (winnerIndex == expectedWinnerIndex)
                System.out.println("Success");
    }

}
