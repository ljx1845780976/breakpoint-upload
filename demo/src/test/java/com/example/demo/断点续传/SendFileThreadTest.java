package com.example.demo.断点续传;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 **/
class SendFileThreadTest {

    @Test
    void run() {
        SendFileThread sf=new SendFileThread();
        sf.run();
    }
}