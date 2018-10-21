package com.legindus.shoplyft.firebase.models;

import com.legindus.shoplyft.firebase.utils.ModelBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryDocument extends ModelBuilder {
    public String name;
    public List<String> keywords;

    public CategoryDocument() {
    }

    public CategoryDocument(String name, List<String> keywords) {
        this.name = name;
        this.keywords = keywords;
    }

    public static class Builder extends ModelBuilder.Builder<Builder, CategoryDocument> {
        private String name;
        private List<String> keywords = new ArrayList<>();

        public CategoryDocument build() {
            return new CategoryDocument(name, keywords);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setKeywords(String... words) {
            keywords.clear();
            keywords.addAll(Arrays.asList(words));
            return this;
        }

        public Builder addKeywords(String... words) {
            keywords.addAll(Arrays.asList(words));
            return this;
        }
    }
}
