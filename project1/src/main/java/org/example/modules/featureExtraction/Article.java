package org.example.modules.featureExtraction;

import java.util.List;

public class Article {

    private final String country;
    private String title;
    private String body;

    public Article(String country, String title, String body) {
        this.country = country;
        this.title = title;
        this.body = body;
    }

    public String getCountry() {
        return country;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAllText() {
        return title + body;
    }

    public String toString() {
        return "Country: " + country + ", Title: " + title + ", Content: " + body;
    }
}
