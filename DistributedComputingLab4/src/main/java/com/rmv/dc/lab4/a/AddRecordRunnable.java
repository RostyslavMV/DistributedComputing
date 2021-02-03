package com.rmv.dc.lab4.a;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddRecordRunnable implements Runnable {

    private final String fileName;
    private final String fullName;
    private final String number;

    private BufferedWriter bufferedWriter;

    public AddRecordRunnable(String fileName, String fullName, String number) {
        this.fileName = fileName;
        this.fullName = fullName;
        this.number = number;
    }

    @Override
    public void run() {
        addRecord(fullName, number);
    }

    private void addRecord(String fullName, String number) {
        DBManager.getLock().writeLock().lock();
        try {
            File file = new File(fileName);
            try {
                FileWriter fileWriter = new FileWriter(file, true);
                bufferedWriter = new BufferedWriter(fileWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bufferedWriter.newLine();
            bufferedWriter.write(fullName + " - " + number);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DBManager.getLock().writeLock().unlock();
    }
}
