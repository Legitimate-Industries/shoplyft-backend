package com.legindus.shoplyft;

import com.legindus.shoplyft.firebase.FirebaseRegistry;

public class Main {
    private FirebaseRegistry registry;

    public final static Object lock = new Object();
    public static volatile boolean die = false;

    public Main() throws InterruptedException {
        registry = new FirebaseRegistry();

        while (!die) {
            synchronized (lock) {
                lock.wait();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Main();
    }
}
