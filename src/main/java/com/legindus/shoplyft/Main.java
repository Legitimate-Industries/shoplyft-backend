package com.legindus.shoplyft;

import com.legindus.shoplyft.firebase.FirebaseRegistry;
import com.legindus.shoplyftqueue.QueryQueue;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    public static FirebaseRegistry registry;
    public static QueryQueue queue;

    public final static Object lock = new Object();
    public static volatile boolean die = false;

    public Main() {

    }

    @Override
    public void run(String... args) throws Exception {
        registry = new FirebaseRegistry();
        queue = new QueryQueue(registry.getDocs());
        while(!die) {
            synchronized (lock) {
                lock.wait();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);
    }
}
