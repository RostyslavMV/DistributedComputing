package com.rmv.dc.lab4.a;

import lombok.Getter;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DBManager {
    @Getter
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void deleteEmptyLines(String fileName){
        lock.writeLock().lock();
        File file = new File(fileName);
        File tempFile = new File("temp");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile));
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                if (readLine.isEmpty()) continue;
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
        lock.writeLock().unlock();
    }
}
