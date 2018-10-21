package com.legindus.shoplyft.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Firebase {
    private Logger LOG = LoggerFactory.getLogger(Firebase.class);
    private FirebaseApp app;
    private DatabaseReference ref;

    public Firebase() {
        app = FirebaseApp.initializeApp(getOptions());
        ref = FirebaseDatabase.getInstance().getReference("categories");
    }

    public FirebaseApp getApp() {
        return app;
    }

    public DatabaseReference getReference() {
        return ref;
    }

    private FirebaseOptions getOptions() {
        FirebaseOptions options = null;

        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .setDatabaseUrl("https://legindus-hacktx-2018.firebaseio.com")
                    .build();
        } catch (IOException e) {
            LOG.error("Error connecting to firebase: ", e);
        }

        return options;
    }
}
