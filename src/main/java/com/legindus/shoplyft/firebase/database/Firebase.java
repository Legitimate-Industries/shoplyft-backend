package com.legindus.shoplyft.firebase.database;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Firebase {
    private Logger LOG = LoggerFactory.getLogger(Firebase.class);
    private FirebaseApp app;
    private FirebaseDatabase ref;

    public Firebase() {
        app = FirebaseApp.initializeApp(getOptions());
        ref = FirebaseDatabase.getInstance();
    }

    public FirebaseApp getApp() {
        return app;
    }

    public FirebaseDatabase getFDatabase() {
        return ref;
    }

    private FirebaseOptions getOptions() {
        FirebaseOptions options = null;

        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream(new File(System.getProperty("os.name").toLowerCase().contains("win") ? "service.json" : "/home/g174v1d_w4ng/service-acc.json"))))
                    .setDatabaseUrl("https://legindus-hacktx-2018.firebaseio.com")
                    .build();
        } catch (IOException e) {
            LOG.error("Error connecting to firebase: ", e);
        }

        return options;
    }
}
