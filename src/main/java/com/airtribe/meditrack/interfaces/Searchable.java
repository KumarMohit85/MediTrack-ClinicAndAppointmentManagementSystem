package com.airtribe.meditrack.interfaces;

public interface Searchable {
    boolean matches(String keyword);

    String getSearchableFields();

    void printSearchResult();
}
