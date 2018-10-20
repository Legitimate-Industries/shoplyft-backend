package com.legindus.shoplyft;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private Logger LOG = LoggerFactory.getLogger(Main.class);
    private FirebaseOptions options;

    public Main() {
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .setDatabaseUrl("legindus-hacktx-2018.firebaseapp.com")
                    .build();

            FirebaseApp.initializeApp();
        } catch (IOException e) {
            LOG.error("Error connecting to firebase: ", e);
        }

    }

    public static void main(String[] args) {
        new Main();
    }
}
