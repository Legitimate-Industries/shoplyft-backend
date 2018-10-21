package com.legindus.shoplyft.firebase.listeners;

import com.google.firebase.database.DataSnapshot;

public abstract class ChildAddedListener extends StrippedListener {
    @Override
    public abstract void onChildAdded(DataSnapshot snapshot, String previousChildName);
}
