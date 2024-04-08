package org.example.modules.featureExtraction;

import org.example.modules.featureExtraction.exceptions.ArticleManagerException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
public class ArticleManager {
    private static ArticleManager instance;

    private final List<Article> articles = new ArrayList<>();

    private ArticleManager() {
    }

    public static ArticleManager getInstance() {
        if (instance == null) {
            instance = new ArticleManager();
        }
        return instance;
    }

    public void loadFromFiles(String pathToDirectory) throws ArticleManagerException {
        File directory = new File(pathToDirectory);
        File[] files = directory.listFiles();
        if (files == null) {
            throw new ArticleManagerException("Directory is empty");
        }

        for (File file : files) {
            System.out.println(file.getName());
            if(!file.getName().endsWith(".sgm"))
                continue;

            List<String> lines;
            try {
                lines = Files.readAllLines(file.toPath(), StandardCharsets.ISO_8859_1);
            } catch (IOException e) {
                throw new ArticleManagerException("Error while reading file: " + file.getName(), e);
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < lines.size(); i++) {
                sb.append(lines.get(i));
            }
            String rawContent = sb.toString();
            String[] rawArticles = rawContent.split("<REUTERS");

            for(String content : rawArticles) {
                if (content.isBlank())
                    continue;
                content = content.split("</REUTERS>")[0];
                String title;
                String body;
                String places;
                try {
                    title = content.split("<TITLE>")[1].split("</TITLE>")[0];
                    body = content.split("<BODY>")[1].split("</BODY>")[0];
                    places = content.split("<PLACES>")[1].split("</PLACES>")[0];
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
                String[] d = places.split("<D>");
                if(d.length > 2 || d.length == 1) {
                    continue;
                }
                String place = d[1].split("</D>")[0];
                Country country;
                try {
                    country = Country.valueOf(place);
                } catch (IllegalArgumentException e) {
                    continue;
                }
                for(String stopWord : StopWordsManager.getInstance().getStopWords()) {
                    title = title.replaceAll("\\b" + stopWord + "\\b", "");
                    body = body.replaceAll("\\b" + stopWord + "\\b", "");
                }
                articles.add(new Article(country, title, body));
            }
        }
    }

    public List<Article> getArticles() {
        return articles.stream().toList();
    }

}
