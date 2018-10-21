package com.legindus.shoplyft.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.legindus.shoplyft.firebase.database.Firebase;
import com.legindus.shoplyftsearch.CategoryDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FirebaseRegistry {
    private Logger LOG = LoggerFactory.getLogger(FirebaseRegistry.class);
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Firebase firebase;

    public FirebaseRegistry() {
        firebase = new Firebase();
        loadDocuments();
    }

    public List<CategoryDocument> getDocs() {
        Future<List<CategoryDocument>> future = loadDocuments();
        List<CategoryDocument> docs = new ArrayList<>();

        try {
            docs = future.get();
        } catch (Exception e) {
            LOG.error("Error grabbing documents: ", e);
        }

        return docs;
    }

    public Firebase getFirebase() {
        return firebase;
    }

    private Future<List<CategoryDocument>> loadDocuments() {
        return executor.submit(() -> {
            List<CategoryDocument> docs = new ArrayList<>();

            firebase.getReference().orderByChild("name").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                    CategoryDocument document = snapshot.getValue(CategoryDocument.class);
                    docs.add(document);
                }

                @Override
                public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

                }

                @Override
                public void onChildRemoved(DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });

            return docs;
        });
    }
}
