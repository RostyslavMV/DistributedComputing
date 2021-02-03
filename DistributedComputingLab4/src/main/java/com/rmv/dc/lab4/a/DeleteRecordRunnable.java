package com.rmv.dc.lab4.a;

import java.io.*;
import java.nio.file.Files;

public class DeleteRecordRunnable implements Runnable {

    private final String fullNameToRemove;

    private final File file;

    public DeleteRecordRunnable(String fileName, String fullNameToRemove) {
        this.fullNameToRemove = fullNameToRemove;
        this.file = new File(fileName);
    }

    @Override
    public void run() {
        removeRecordByFullName(fullNameToRemove);
        System.out.println("Removed " + fullNameToRemove + " phone number");
    }

    private void removeRecordByFullName(String fullNameToRemove) {
        DBManager.getLock().writeLock().lock();
        File tempFile = new File("myTempFile.txt");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String readLine;
            String[] splittedLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                splittedLine = readLine.split(" - ");
                String trimmedLine = splittedLine[0].trim();
                if (trimmedLine.equals(fullNameToRemove)) continue;
                bufferedWriter.write(readLine + System.getProperty("line.separator"));
            }
            bufferedReader.close();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.move(tempFile.toPath(), file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DBManager.getLock().writeLock().unlock();
    }
}
