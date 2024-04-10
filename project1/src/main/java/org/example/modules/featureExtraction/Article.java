package org.example.modules.featureExtraction;

import java.util.List;

public class Article {

    private final String country;
    private List<String> title;
    private List<String> body;

    public Article(String country, List<String> title, List<String> content) {
        this.country = country;
        this.title = title;
        this.body = content;
    }

    public String getCountry() {
        return country;
    }

    public List<String> getTitle() {
        return title;
    }

    public List<String> getBody() {
        return body;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public void setBody(List<String> body) {
        this.body = body;
    }

    public String toString() {
        return "Country: " + country + ", Title: " + title + ", Content: " + body;
    }
}
