package com.rmv.dc.lab2.a;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
public class ForestTasksManager {

    private final boolean[][] tasksField;
    private int currentTaskNumber = -1;
    @Setter
    private boolean done = false;

    public ForestTasksManager(int ySize, int xSize) {
        if (xSize <= 0) {
            xSize = 100;
        }
        if (ySize <= 0) {
            ySize = 100;
        }
        tasksField = new boolean[ySize][xSize];
        placeBear();
    }

    public synchronized Task getTask() {
        if (currentTaskNumber + 1 < tasksField.length) {
            return new Task(tasksField[++currentTaskNumber],currentTaskNumber);
        }
        return null;
    }

    private void placeBear() {
        Random random = new Random(System.currentTimeMillis());
        int randomX = random.nextInt(tasksField.length - 1);
        int randomY = random.nextInt(tasksField[0].length - 1);
        tasksField[randomY][randomX] = true;
    }
}
