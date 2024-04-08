package org.example;

import org.example.modules.featureExtraction.ArticleManager;
import org.example.modules.featureExtraction.FeaturesManager;
import org.example.modules.featureExtraction.StopWordsManager;
import org.example.modules.featureExtraction.exceptions.ArticleManagerException;
import org.example.modules.featureExtraction.exceptions.StopWordsManagerException;

public class Main {
    public static void main(String[] args) throws ArticleManagerException, StopWordsManagerException {
        StopWordsManager.getInstance().loadFromFile("/Users/igorbobrukiewicz/Downloads/stopwords.txt");
        ArticleManager articleManager = ArticleManager.getInstance();
        articleManager.loadFromFiles("/Users/igorbobrukiewicz/Downloads/reuters+21578+text+categorization+collection/reuters21578");
        articleManager.getArticles().forEach(article -> System.out.println(FeaturesManager.getInstance().extractFeatures(article).toString()));
    }
}