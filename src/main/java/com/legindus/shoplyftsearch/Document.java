package com.legindus.shoplyftsearch;

import java.util.List;

public class Document {
    String categoryName;
    List<String> keywords;

    /**
     *  Document constructor
     *
     * @param name of the category.
     * @param keywords related to the document.
     */
    public Document(String name, List<String> keywords) {
        this.categoryName = name;
        this.keywords = keywords;
    }


}
