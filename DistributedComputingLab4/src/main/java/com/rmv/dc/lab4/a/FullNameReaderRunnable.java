package com.rmv.dc.lab4.a;

import java.io.*;

public class FullNameReaderRunnable extends ReaderService implements Runnable {

    private final String phoneNumber;

    public FullNameReaderRunnable(String fileName, String phoneNumber) {
        super(fileName);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void run() {
        System.out.println(findFullNameByPhoneNumber(phoneNumber));
    }

    private String findFullNameByPhoneNumber(String phoneNumber) {
        DBManager.getLock().readLock().lock();
        try {
            inputFile = new File(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
            String readLine;
            String[] splittedLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                splittedLine = readLine.split(" ");
                if (splittedLine[splittedLine.length - 1].equals(phoneNumber)) {
                    bufferedReader.close();
                    DBManager.getLock().readLock().unlock();
                    return phoneNumber + "'s full name is " + splittedLine[0]
                            + " " + splittedLine[1] + " " + splittedLine[2];
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DBManager.getLock().readLock().unlock();
        return phoneNumber + "'s full name isn't present in the file";
    }
}
