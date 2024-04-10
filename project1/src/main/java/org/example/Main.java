package org.example;

import org.example.modules.featureExtraction.FeatureExtractionModule;
import org.example.modules.featureExtraction.exceptions.ArticleManagerException;
import org.example.modules.featureExtraction.exceptions.StopWordsManagerException;

public class Main {
    public static void main(String[] args) throws ArticleManagerException, StopWordsManagerException {
        final String STOP_WORDS_PATH = "/Users/igorbobrukiewicz/Downloads/stopwords.txt";
        final String ARTICLES_PATH = "/Users/igorbobrukiewicz/Downloads/reuters+21578+text+categorization+collection/reuters21578";
        FeatureExtractionModule featureExtractionModule = new FeatureExtractionModule(STOP_WORDS_PATH, ARTICLES_PATH);
        featureExtractionModule.getExtractedFeatures().forEach(System.out::println);
    }
}