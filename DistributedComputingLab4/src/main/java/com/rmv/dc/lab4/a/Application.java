package com.rmv.dc.lab4.a;

public class Application {

    public static void main(String[] args) {
        Thread[] threads = new Thread[12];
        threads[0] = new Thread(new FullNameReaderRunnable("input.txt", "123456"));
        threads[1] = new Thread(new FullNameReaderRunnable("input.txt", "789123"));
        threads[2] = new Thread(new FullNameReaderRunnable("input.txt", "456789"));
        threads[3] = new Thread(new PhoneNumbersReaderRunnable("input.txt", "F1"));
        threads[4] = new Thread(new PhoneNumbersReaderRunnable("input.txt", "F2"));
        threads[5] = new Thread(new PhoneNumbersReaderRunnable("input.txt", "F6"));
        threads[6] = new Thread(new  AddRecordRunnable("input.txt","F7 I O","004455"));
        threads[7] = new Thread(new  AddRecordRunnable("input.txt","F8 I O","224455"));
        threads[8] = new Thread(new  AddRecordRunnable("input.txt","F9 I O","334455"));
        threads[9] = new Thread(new DeleteRecordRunnable("input.txt", "F2 I O"));
        threads[10] = new Thread(new DeleteRecordRunnable("input.txt", "F1 I O"));
        threads[11] = new Thread(new DeleteRecordRunnable("input.txt", "F3 I O"));


        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DBManager.deleteEmptyLines("input.txt");
    }
}
