package com.rmv.dc.lab4.a;


import java.io.*;

public class PhoneNumbersReaderRunnable extends ReaderService implements Runnable {

    private final String surname;

    public PhoneNumbersReaderRunnable(String fileName, String surname) {
        super(fileName);
        this.surname = surname;
    }

    @Override
    public void run() {
        System.out.println(findPhoneNumberBySurname(surname));
    }

    private String findPhoneNumberBySurname(String surname) {
        DBManager.getLock().readLock().lock();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            inputFile = new File(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
            String readLine;
            String[] splittedLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                splittedLine = readLine.split(" ");
                if (splittedLine[0].equals(surname)) {
                    stringBuilder.append(splittedLine[splittedLine.length - 1]).append(", ");
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = stringBuilder.toString();
        DBManager.getLock().readLock().unlock();
        if (result.equals("")) {
            return surname + "'s phone numbers aren't present in the file";
        }
        return surname + "'s phone numbers is " + result;
    }

}
