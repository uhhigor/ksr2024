package org.example.modules.featureExtraction;

import org.example.modules.featureExtraction.exceptions.StopWordsManagerException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class StopWordsManager {
    private static StopWordsManager instance;

    private List<String> stopWords = new ArrayList<>();

    private StopWordsManager() {
    }

    public static StopWordsManager getInstance() {
        if (instance == null) {
            instance = new StopWordsManager();
        }
        return instance;
    }

    public void loadFromFile(String path) throws StopWordsManagerException {
        try {
            stopWords = Files.readAllLines(Path.of(path));
        } catch (Exception e) {
            throw new StopWordsManagerException("Error while reading stop words file", e);
        }
    }

    public List<String> getStopWords() {
        return stopWords.stream().toList();
    }

}
