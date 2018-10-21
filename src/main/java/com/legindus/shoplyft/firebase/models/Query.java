package com.legindus.shoplyft.firebase.models;

import java.util.List;

public class Query {
    public int status;
    public List<String> chat;

    public Query() {
    }

    public Query(int status, List<String> chat) {
        this.status = status;
        this.chat = chat;
    }
}
