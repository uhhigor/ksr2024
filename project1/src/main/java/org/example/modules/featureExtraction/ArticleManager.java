package org.example.modules.featureExtraction;

import org.example.modules.featureExtraction.exceptions.ArticleManagerException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
class ArticleManager {
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
                String titleText;
                String bodyText;
                String placesText;
                try {
                    titleText = content.split("<TITLE>")[1].split("</TITLE>")[0];
                    bodyText = content.split("<BODY>")[1].split("</BODY>")[0];
                    placesText = content.split("<PLACES>")[1].split("</PLACES>")[0];
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }

                String[] d = placesText.split("<D>");
                if(d.length != 2) {
                    continue;
                }
                String place = d[1].split("</D>")[0];
                String country;
                if(place.equals("west-germany") || place.equals("usa") || place.equals("france") || place.equals("uk") || place.equals("canada") || place.equals("japan")) {
                    country = place;
                } else {
                    continue;
                }

                String title = NLPUtils.getInstance().preProcessText(titleText);
                String body = NLPUtils.getInstance().preProcessText(bodyText);

                articles.add(new Article(country, title, body));
            }
            System.out.println("Articles loaded: " + rawArticles.length);
        }
    }

    public List<Article> getArticles() {
        return articles.stream().toList();
    }

}
