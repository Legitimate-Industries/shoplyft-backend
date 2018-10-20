package com.legindus.shoplyft.firebase;

import com.google.firebase.database.DatabaseReference;
import com.legindus.shoplyft.firebase.database.Firebase;

public class FirebaseRegistry {
    private Firebase database;
    private DatabaseReference ref;

    public FirebaseRegistry() {
        database = new Firebase();
    }

    public Firebase getDatabase() {
        return database;
    }
}
