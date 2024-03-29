package com.rmv.dc.lab6.a;

import javax.swing.*;
import java.util.concurrent.CyclicBarrier;

public class Manager {
  static final int CIVILIZATION_NUMBER = 1;
  static final int ROWS = 90;
  static final int COLUMNS = 50;
  static final int THREAD_NUM = 4;
  static final int TASK_SIZE = 20;
  static volatile boolean updated = true;
  private int cell_size;

  Manager(int cell_size) {
    this.cell_size = cell_size;
  }

  public void work() {
    Buffer buffer = new Buffer();
    CyclicBarrier barrier = new CyclicBarrier(THREAD_NUM + 1, () -> updated = false);
    Simulation simulation = new Simulation(buffer, barrier, ROWS, COLUMNS);
    Drawing drawing = new Drawing(buffer, cell_size);
    JFrame frame = new JFrame();
    frame.setSize((COLUMNS) * cell_size, (ROWS + 3) * cell_size);
    frame.getContentPane().add(drawing);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    Thread draw = new Thread(drawing);
    Thread simulate = new Thread(simulation);
    SimulationThread[] threads = new SimulationThread[THREAD_NUM];
    int generated = 0;
    for (int i = 0; i < THREAD_NUM - 1; ++i) {
      threads[i] = new SimulationThread(barrier, simulation, generated, TASK_SIZE);
      generated += TASK_SIZE;
    }
    threads[THREAD_NUM - 1] =
        new SimulationThread(barrier, simulation, generated, ROWS - generated);
    simulate.start();
    draw.start();
    for (int i = 0; i < THREAD_NUM; ++i) {
      threads[i].start();
    }
    while (true) {
      Thread.yield();
    }
  }
}
