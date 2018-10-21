package com.legindus.shoplyft.firebase.models;

import com.legindus.shoplyft.firebase.utils.ModelBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Query extends ModelBuilder {
    public long id;
    public long timestamp;
    public int status;
    public String first;
    public String employeeName;
    public List<String> chat;

    public Query() {
    }

    public Query(long id, long timestamp, int status, String first, String employeeName, List<String> chat) {
        this.id = id;
        this.timestamp = timestamp;
        this.status = status;
        this.first = first;
        this.employeeName = employeeName;
        this.chat = chat;
    }

    public static class Builder extends ModelBuilder.Builder<Builder, Query> {
        private long id;
        private long timestamp;
        private int status;
        private String first;
        private String employeeName;
        private List<String> chat = new ArrayList<>();

        @Override
        public Query build() {
            return new Query(id, timestamp, status, first, employeeName, chat);
        }

        public final Builder setId(long id) {
            this.id = id;
            return this;
        }

        public final Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public final Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public final Builder setFirst(String first) {
            this.first = first;
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
