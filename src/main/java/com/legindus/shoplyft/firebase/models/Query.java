package com.legindus.shoplyft.firebase.models;

import com.legindus.shoplyft.firebase.utils.ModelBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Query extends ModelBuilder {
    public long timestamp;
    public int status;
    public String id;
    public String first;
    public String employeeName;
    public List<String> chat;

    public Query() {
    }

    public Query(int status, String employeeName, List<String> chat) {
        this.timestamp = System.currentTimeMillis();
        this.status = status;
        this.first = chat.get(0);
        this.employeeName = employeeName;
        this.chat = chat;
    }

    public static class Builder extends ModelBuilder.Builder<Builder, Query> {
        private int status;
        private String employeeName;
        private List<String> chat = new ArrayList<>();

        @Override
        public Query build() {
            return new Query(status, employeeName, chat);
        }

        public final Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public final Builder setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
            return this;
        }

        public final Builder setChat(String... messages) {
            this.chat.clear();
            this.chat.addAll(Arrays.asList(messages));
            return this;
        }

        public final Builder addChat(String... messages) {
            this.chat.addAll(Arrays.asList(messages));
            return this;
        }
    }
}
