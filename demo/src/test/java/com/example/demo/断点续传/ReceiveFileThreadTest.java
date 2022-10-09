package com.example.demo.断点续传;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 **/
class ReceiveFileThreadTest {

    @Test
    void run() {
        ReceiveFileThread rf=new ReceiveFileThread();
        rf.run();
    }
}