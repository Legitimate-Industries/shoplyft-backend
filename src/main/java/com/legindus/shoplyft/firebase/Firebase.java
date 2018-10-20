package com.legindus.shoplyft.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Firebase {
    private Logger LOG = LoggerFactory.getLogger(Firebase.class);
    private FirebaseApp app;

    public Firebase() {
        app = FirebaseApp.initializeApp(getOptions());

        System.out.println(app.getName());
    }

    private FirebaseOptions getOptions() {
        FirebaseOptions options = null;

        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .setDatabaseUrl("legindus-hacktx-2018.firebaseapp.com")
                    .build();
        } catch (IOException e) {
            LOG.error("Error connecting to firebase: ", e);
        }

        return options;
    }
}
