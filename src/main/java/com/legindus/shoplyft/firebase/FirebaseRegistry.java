package com.legindus.shoplyft.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.legindus.shoplyft.firebase.database.Firebase;
import com.legindus.shoplyft.firebase.listeners.ChildAddedListener;
import com.legindus.shoplyft.firebase.models.CategoryDocument;
import com.legindus.shoplyft.firebase.models.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class FirebaseRegistry {
    private Logger LOG = LoggerFactory.getLogger(FirebaseRegistry.class);
    private Firebase firebase;

    public FirebaseRegistry() {
        firebase = new Firebase();
    }

    @Nullable
    public List<CategoryDocument> getDocs() throws ExecutionException, InterruptedException {
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
                LOG.error("Firebase grab cancelled: ", error);
            }
        });

        int ct = count.get();
        CompletableFuture<List<CategoryDocument>> listFuture = new CompletableFuture<>();
        AtomicInteger numReceived = new AtomicInteger(0);
        firebase.getFDatabase().getReference("categories").addChildEventListener(new ChildAddedListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                CategoryDocument document = snapshot.getValue(CategoryDocument.class);
                int res = numReceived.incrementAndGet();
                docs.add(document);

                if (res == ct)
                    listFuture.complete(docs);
            }
        });

        return listFuture.get();
    }

    @Nullable
    public Query getQuery(String id) throws ExecutionException, InterruptedException {
        CompletableFuture<Query> future = new CompletableFuture<>();
        firebase.getFDatabase().getReference("queries").addChildEventListener(new ChildAddedListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                if (snapshot.getKey().equals(id))
                    future.complete(snapshot.getValue(Query.class));
            }
        });

        return future.get();
    }

    public void newQuery(Query query, String id) {
        firebase.getFDatabase().getReference("queries").child(id).setValueAsync(query);
    }

    public void updateQuery(Query newQuery, String id) {
        DatabaseReference r = firebase.getFDatabase().getReference("queries");
        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(id))
                    r.setValueAsync(newQuery);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                LOG.error("Firebase grab cancelled: ", error);
            }
        });
    }

    public void removeQuery(String id) {
        firebase.getFDatabase().getReference("queries").child(id).removeValueAsync();
    }

    public Firebase getFirebase() {
        return firebase;
    }
}
