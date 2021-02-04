package com.rmv.dc.lab1.b;

import com.rmv.dc.lab1.MyRunnable;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Application {

    private static final AtomicInteger semaphore = new AtomicInteger(0);

    private static final JSlider slider = new JSlider();

    private static final Thread thread10 = new Thread(new MyRunnable(10, slider));
    private static final Thread thread90 = new Thread(new MyRunnable(90, slider));

    public static void main(String[] args) {

        JFrame frame = new JFrame("Lab1 Part B");
        slider.setEnabled(false);
        slider.setBounds(85, 30, 220, 20);

        JLabel label = new JLabel("");
        label.setBounds(100, 70, 200, 30);

        JButton startThread10Button = new JButton("Start 10");
        startThread10Button.setBounds(10, 110, 150, 30);

        JButton stopThread10Button = new JButton("Stop 10");
        stopThread10Button.setBounds(10, 150, 150, 30);

        JButton startThread90Button = new JButton("Start 90");
        startThread90Button.setBounds(230, 110, 150, 30);

        JButton stopThread90Button = new JButton("Stop 90");
        stopThread90Button.setBounds(230, 150, 150, 30);


        startThread10Button.addActionListener(e -> {
            if (semaphore.compareAndSet(0,1)) {
                label.setText("Slider is being used by a thread");
                thread10.setPriority(Thread.MIN_PRIORITY);
                thread10.start();
                stopThread90Button.setEnabled(false);
            }
        });
        stopThread10Button.addActionListener(e -> {
            if (semaphore.compareAndSet(1,0)) {
                label.setText("");
                thread10.interrupt();
                stopThread90Button.setEnabled(true);
            }
        });
        startThread90Button.addActionListener(e -> {
            if (semaphore.compareAndSet(0,1)) {
                label.setText("Slider is being used by a thread");
                thread90.setPriority(Thread.MAX_PRIORITY);
                thread90.start();
                stopThread10Button.setEnabled(false);
            }
        });
        stopThread90Button.addActionListener(e -> {
            if (semaphore.compareAndSet(1,0)) {
                label.setText("");
                thread90.interrupt();
                stopThread10Button.setEnabled(true);
            }
        });

        frame.add(label);
        frame.add(startThread10Button);
        frame.add(stopThread10Button);
        frame.add(startThread90Button);
        frame.add(stopThread90Button);
        frame.add(slider);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setVisible(true);
    }

}
