package com.legindus.shoplyft;

import com.legindus.shoplyft.firebase.FirebaseRegistry;

public class Main {
    public static FirebaseRegistry registry;

    public final static Object lock = new Object();
    public static volatile boolean die = false;

    public static void main(String[] args) throws InterruptedException {
        registry = new FirebaseRegistry();

        while (!die) {
            synchronized (lock) {
                lock.wait();
            }
        }
    }
}
