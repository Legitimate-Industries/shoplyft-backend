package com.legindus.shoplyftsearch;

import java.util.List;

public class CategoryDocument {
    public String categoryName;
    public List<String> keywords;

    public CategoryDocument(String name, List<String> keywords) {
        this.categoryName = name;
        this.keywords = keywords;
    }
}
