package com.company;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by gyfleo on 2017/12/4.
 */
public class Main {
    public static void main(String[] argv) throws IOException, TimeoutException {
        Customer.customer();
        Producer.producer();
    }
}
