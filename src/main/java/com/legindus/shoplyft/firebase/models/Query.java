package com.legindus.shoplyft.firebase.models;

import com.legindus.shoplyft.firebase.utils.ModelBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Query extends ModelBuilder {
    public int status;
    public List<String> chat;

    public Query() {
    }

    public Query(int status, List<String> chat) {
        this.status = status;
        this.chat = chat;
    }

    public static class Builder extends ModelBuilder.Builder<Builder, Query> {
        private int status;
        private List<String> chat = new ArrayList<>();

        @Override
        public Query build() {
            return new Query(status, chat);
        }

        public final Builder setStatus(int status) {
            this.status = status;
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
