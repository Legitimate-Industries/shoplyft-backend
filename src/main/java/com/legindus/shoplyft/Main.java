package com.legindus.shoplyft;

import com.legindus.shoplyft.firebase.FirebaseRegistry;

public class Main {
    private FirebaseRegistry registry;

    public Main() {
        registry = new FirebaseRegistry();
    }

    public static void main(String[] args) {
        new Main();
    }
}
