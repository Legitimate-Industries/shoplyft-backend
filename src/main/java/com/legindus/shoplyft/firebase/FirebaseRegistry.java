package com.legindus.shoplyft.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.legindus.shoplyft.firebase.Firebase;
import com.legindus.shoplyftsearch.CategoryDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FirebaseRegistry {
    private Logger LOG = LoggerFactory.getLogger(FirebaseRegistry.class);
    private Firebase firebase;
    private List<CategoryDocument> docs;

    public FirebaseRegistry() {
        firebase = new Firebase();
        loadDocuments();
    }

    public List<CategoryDocument> getDocs() {
        return docs;
    }

    private void loadDocuments() {
        firebase.getReference().orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                CategoryDocument doc = snapshot.getValue(CategoryDocument.class);
                docs.add(doc);
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
                LOG.error("Firebase error: ", error);
            }
        });
    }
}
