package com.rmv.dc.lab4.a;

import java.io.*;

public class ReaderService {

    protected String fileName;
    protected File inputFile;
    protected BufferedReader bufferedReader;

    public ReaderService(String fileName) {
        this.fileName = fileName;
    }
}
