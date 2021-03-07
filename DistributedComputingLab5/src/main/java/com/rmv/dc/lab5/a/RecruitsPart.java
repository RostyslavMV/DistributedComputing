package com.rmv.dc.lab5.a;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class RecruitsPart implements Runnable {

    private static final ArrayList<Boolean> partFinished = new ArrayList<>();

    private static final AtomicBoolean finished = new AtomicBoolean(false);

    private final int[] recruits;
    private final int partIndex;
    private final int leftIndex;
    private final int rightIndex;
    private final MyCyclicBarrier cyclicBarrier;

    @Override
    public void run() {
        while (!finished.get()) {
            boolean thisPartFinished = partFinished.get(partIndex);
            if (!thisPartFinished) {
                System.out.println(Arrays.toString(recruits));
                boolean formatted = true;
                for (int i = leftIndex; i < rightIndex - 1; i++) {
                    if (recruits[i] != recruits[i + 1]) {
                        recruits[i] *= -1;
                        formatted = false;
                    }
                }
                if (formatted) {
                    finish();
                }
            }
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    private void finish() {
        System.out.println("Part " + partIndex + " finished");
        partFinished.set(partIndex, true);

        for (boolean currentPartFinished : partFinished) {
            if (!currentPartFinished) {
                return;
            }
        }
        finished.set(true);
    }

    public static void fillFinishedArray(int partsNumber) {
        for (int i = 0; i < partsNumber; i++) {
            partFinished.add(false);
        }
    }
}
