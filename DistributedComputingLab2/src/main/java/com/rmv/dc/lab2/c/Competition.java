package com.rmv.dc.lab2.c;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.concurrent.RecursiveTask;

@AllArgsConstructor
public class Competition extends RecursiveTask<Integer> {
    @Setter
    private static int[] monksQiPower;

    private final int leftIndex;
    private final int rightIndex;

    @Override
    protected Integer compute() {
        if (rightIndex - leftIndex == 1) {
            return getWinnerFromDuel(leftIndex, rightIndex);
        }
        if(leftIndex == rightIndex){
            return leftIndex;
        }
        int middleIndex = (leftIndex + rightIndex)/2;
        Competition leftPartCompetition = new Competition(leftIndex, middleIndex);
        Competition rightPartCompetition = new Competition(middleIndex+1, rightIndex);
        leftPartCompetition.fork();
        rightPartCompetition.fork();
        int leftCompetitionWinnerIndex = leftPartCompetition.join();
        int rightCompetitionWinnerIndex = rightPartCompetition.join();
        return getWinnerFromDuel(leftCompetitionWinnerIndex, rightCompetitionWinnerIndex);
    }

    private int getWinnerFromDuel(int index1, int index2){
        return monksQiPower[index1] > monksQiPower[index2] ? index1 : index2;
    }
}
