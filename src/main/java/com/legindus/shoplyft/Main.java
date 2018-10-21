package com.legindus.shoplyft;

import com.legindus.shoplyft.firebase.FirebaseRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {
    private FirebaseRegistry registry;

    public Main() {
//        registry = new FirebaseRegistry();
//        System.out.println(registry.getDocs());
    }

    @Override
    public void run(String... args) throws Exception {

    }

    public static void main(String[] args) throws Exception {
//        new Main();
//        new RestServer().start();
        SpringApplication.run(Main.class);
    }
}
