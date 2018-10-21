package com.legindus.shoplyft;

import com.legindus.shoplyft.firebase.FirebaseRegistry;
import com.legindus.shoplyft.rest.RestServer;

public class Main {
    private FirebaseRegistry registry;

    public Main() {
        registry = new FirebaseRegistry();
        System.out.println(registry.getDocs());
    }

    public static void main(String[] args) throws Exception {
//        new Main();
        new RestServer().start();
    }
}
