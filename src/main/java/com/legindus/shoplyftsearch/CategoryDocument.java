package com.legindus.shoplyftsearch;

import java.util.List;

public class CategoryDocument {
    public String name;
    public List<String> keywords;

    public CategoryDocument() {}

    public CategoryDocument(String name, List<String> keywords) {
        this.name = name;
        this.keywords = keywords;
    }
}
