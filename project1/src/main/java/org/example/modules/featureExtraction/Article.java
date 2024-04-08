package org.example.modules.featureExtraction;

public class Article {

    private final Country country;
    private String title;
    private String body;

    public Article(Country country, String title, String content) {
        this.country = country;
        this.title = title;
        this.body = content;
    }

    public Country getCountry() {
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

    public String toString() {
        return "Country: " + country + ", Title: " + title + ", Content: " + body;
    }
}
