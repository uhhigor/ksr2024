package org.example.modules.featureExtraction;

import org.example.modules.featureExtraction.exceptions.StopWordsManagerException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class NLPUtils {
    private static NLPUtils instance;

    private List<String> stopWords = new ArrayList<>();

    private NLPUtils() {
    }

    public static NLPUtils getInstance() {
        if (instance == null) {
            instance = new NLPUtils();
        }
        return instance;
    }

    public void loadStopWordsFromFile(String path) throws StopWordsManagerException {
        try {
            stopWords = Files.readAllLines(Path.of(path));
        } catch (Exception e) {
            throw new StopWordsManagerException("Error while reading stop words file", e);
        }
    }

    public List<String> tokenize(String text) {
        text = text.toLowerCase().replaceAll("[^a-zA-Z0-9]", " ");
        return new ArrayList<>(List.of(text.split("\\s+")));
    }

    public List<String> removeStopWords(List<String> tokens) {
        tokens.removeAll(stopWords);
        return tokens;
    }

}
