package com.rmv.dc.lab2.a;

import lombok.Getter;
import lombok.Setter;

public class BeeRunnable implements Runnable {
    @Setter
    private static ForestTasksManager forestTasksManager;
    @Getter
    private static int bearsX;
    @Getter
    private static int bearsY;

    @Override
    public void run() {
        while (!forestTasksManager.isDone()) {
            Task currentTask = forestTasksManager.getTask();
            boolean[] field = currentTask.getField();
            if (field == null)
                return;

            for (int i = 0; i < field.length; i++) {
                if (field[i]) {
                    bearsY = currentTask.getY();
                    bearsX = i;
                    forestTasksManager.setDone(true);
                }
            }

        }
    }
}
