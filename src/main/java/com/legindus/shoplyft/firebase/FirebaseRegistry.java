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

    /**
     * Loads database and retrieves count for number of categories. Then retrieves all
     * {@link com.legindus.shoplyft.firebase.models.CategoryDocument} and if the count matches,
     * returns for further use by search.
     *
     * @return Immutable list of {@link com.legindus.shoplyft.firebase.models.CategoryDocument}
     * @throws ExecutionException
     *         If an attempt is made to retrieve result of task that aborted
     * @throws InterruptedException
     *         If future is interrupted while running
     */
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

    /**
     * Grabs a specified User Query with the given unique id.
     * User the returned {@link com.legindus.shoplyft.firebase.models.Query} to
     * provide further details on user status and chat history.
     *
     * @param  id
     *         The User Query id to retrieve
     * @return {@link com.legindus.shoplyft.firebase.models.Query}
     *         User Query containing status and chat history
     * @throws ExecutionException
     *         If an attempt is made to retrieve result of task that aborted
     * @throws InterruptedException
     *         If future is interrupted while running
     */
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

    /**
     * Adds a User Query to the database given the {@link com.legindus.shoplyft.firebase.models.Query}
     * with the specified unique id.
     *
     * @param query
     *        The User Query to add
     * @param id
     *        The specified id of the User Query
     */
    public void newQuery(Query query, String id) {
        firebase.getFDatabase().getReference("queries").child(id).setValueAsync(query);
    }

    /**
     * Updates the User Query with the given unique id with the provided
     * {@link com.legindus.shoplyft.firebase.models.Query} if it exists.
     *
     * Does nothing if the User Query does not exist in the database!
     *
     * @param newQuery
     *        The updated User Query to replace the old one
     * @param id
     *        The id of the User Query to be updated
     */
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

    /**
     * Removes User Query from database given specified unique id.
     *
     * @param id
     *        The specified User Query id to remove
     */
    public void removeQuery(String id) {
        firebase.getFDatabase().getReference("queries").child(id).removeValueAsync();
    }

    public Firebase getFirebase() {
        return firebase;
    }
}
