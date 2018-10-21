package com.legindus.shoplyft.firebase.listeners;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public abstract class StrippedListener implements ChildEventListener {
    @Override
    public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
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
}
