package com.legindus.shoplyft.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.legindus.shoplyft.firebase.database.Firebase;
import com.legindus.shoplyft.firebase.models.CategoryDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class FirebaseRegistry {
    private Logger LOG = LoggerFactory.getLogger(FirebaseRegistry.class);
    private Firebase firebase;

    public FirebaseRegistry() {
        firebase = new Firebase();
    }

    public List<CategoryDocument> getDocs() throws ExecutionException, InterruptedException {
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

    private Future<List<CategoryDocument>> loadDocuments() throws ExecutionException, InterruptedException {
        List<CategoryDocument> docs = new ArrayList<>();

        CompletableFuture<Integer> count = new CompletableFuture<>();
        firebase.getFDatabase().getReference().child("numCategories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int ct = snapshot.getValue(Integer.class);
                count.complete(ct);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        int ct = count.get();
        CompletableFuture<List<CategoryDocument>> listFuture = new CompletableFuture<>();
        AtomicInteger numReceived = new AtomicInteger(0);
        firebase.getFDatabase().getReference("categories").orderByChild("name").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                CategoryDocument document = snapshot.getValue(CategoryDocument.class);
                int res = numReceived.incrementAndGet();
                docs.add(document);

                if (res == ct)
                    listFuture.complete(docs);
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

        return listFuture;
    }
}
