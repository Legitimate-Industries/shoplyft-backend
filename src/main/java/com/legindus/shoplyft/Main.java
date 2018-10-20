package com.legindus.shoplyft;

import com.legindus.shoplyft.firebase.Firebase;

public class Main {
    private Firebase firebase;

    public Main() {
        firebase = new Firebase();
    }

    public static void main(String[] args) {
        new Main();
    }
}
