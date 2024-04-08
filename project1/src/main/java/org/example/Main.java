package org.example;

import org.example.modules.featureExtraction.FeatureExtractionModule;
import org.example.modules.featureExtraction.exceptions.ArticleManagerException;
import org.example.modules.featureExtraction.exceptions.StopWordsManagerException;

public class Main {
    public static void main(String[] args) throws ArticleManagerException, StopWordsManagerException {
        FeatureExtractionModule.loadStopWords("/Users/igorbobrukiewicz/Downloads/stopwords.txt");
        FeatureExtractionModule.loadArticles("/Users/igorbobrukiewicz/Downloads/reuters+21578+text+categorization+collection/reuters21578");
        FeatureExtractionModule.getExtractedFeatures().forEach(System.out::println);
    }
}