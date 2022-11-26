package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchData implements Serializable {

    private ArrayList<String> authors, titles;

    public SearchData(ArrayList<String> authorsWithTitles) {
        separateAuthorsWithTitles(authorsWithTitles);
    }

    private void separateAuthorsWithTitles(ArrayList<String> authorsWithTitles) {
        ArrayList<String> authors = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        int i = 0;
        for (String s : authorsWithTitles) {
            if ((i % 2 == 1)) {
                titles.add(s);
            } else {
                authors.add(s);
            }
            i++;
        }

        setAuthors(authors);
        setTitles(titles);
    }

    private void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    private void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }


}
