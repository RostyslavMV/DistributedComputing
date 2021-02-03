package com.rmv.dc.lab1.a;

import com.rmv.dc.lab1.MyRunnable;

import javax.swing.*;

public class Application {

    private static final JSlider slider = new JSlider();

    private static final Thread thread10 = new Thread(new MyRunnable(10, slider));
    private static final Thread thread90 = new Thread(new MyRunnable(90, slider));

    public static void main(String[] args) {

        JFrame frame = new JFrame("Lab1 Part A");
        slider.setEnabled(false);
        slider.setBounds(85, 30, 220, 20);


        JButton startButton = new JButton("Start");
        startButton.setBounds(150, 70, 90, 30);
        startButton.addActionListener(e -> startThreads());

        JLabel thread10PriorityLabel = new JLabel("10 Priority = " + thread10.getPriority());
        thread10PriorityLabel.setBounds(10, 70, 90, 30);

        JLabel thread90PriorityLabel = new JLabel("90 Priority = " + thread90.getPriority());
        thread90PriorityLabel.setBounds(300, 70, 90, 30);


        JButton increaseThread10PriorityButton = new JButton("10 Priority++");
        increaseThread10PriorityButton.setBounds(10, 110, 150, 30);
        increaseThread10PriorityButton.addActionListener(e -> {
            increaseThreadPriority(thread10);
            thread10PriorityLabel.setText("10 Priority = " + thread10.getPriority());
        });

        JButton decreaseThread10PriorityButton = new JButton("10 Priority--");
        decreaseThread10PriorityButton.setBounds(10, 150, 150, 30);
        decreaseThread10PriorityButton.addActionListener(e -> {
            decreaseThreadPriority(thread10);
            thread10PriorityLabel.setText("10 Priority = " + thread10.getPriority());
        });

        JButton increaseThread90PriorityButton = new JButton("90 Priority++");
        increaseThread90PriorityButton.setBounds(230, 110, 150, 30);
        increaseThread90PriorityButton.addActionListener(e -> {
            increaseThreadPriority(thread90);
            thread90PriorityLabel.setText("90 Priority = " + thread90.getPriority());
        });

        JButton decreaseThread90PriorityButton = new JButton("90 Priority--");
        decreaseThread90PriorityButton.setBounds(230, 150, 150, 30);
        decreaseThread90PriorityButton.addActionListener(e -> {
            decreaseThreadPriority(thread90);
            thread90PriorityLabel.setText("90 Priority = " + thread90.getPriority());
        });

        frame.add(startButton);
        frame.add(thread10PriorityLabel);
        frame.add(thread90PriorityLabel);
        frame.add(increaseThread10PriorityButton);
        frame.add(decreaseThread10PriorityButton);
        frame.add(increaseThread90PriorityButton);
        frame.add(decreaseThread90PriorityButton);
        frame.add(slider);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private static void startThreads() {
        thread10.start();
        thread90.start();
    }

    private static void increaseThreadPriority(Thread thread) {
        if (thread.getPriority() < Thread.MAX_PRIORITY)
            thread.setPriority(thread.getPriority() + 1);
    }

    private static void decreaseThreadPriority(Thread thread) {
        if (thread.getPriority() > Thread.MIN_PRIORITY)
            thread.setPriority(thread.getPriority() - 1);
    }

}
