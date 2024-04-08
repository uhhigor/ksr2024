package org.example.modules.featureExtraction;

import org.example.modules.featureExtraction.exceptions.ArticleManagerException;
import org.example.modules.featureExtraction.exceptions.StopWordsManagerException;

import java.util.ArrayList;
import java.util.List;

public class FeatureExtractionModule {
    public static void loadStopWords(String path) throws StopWordsManagerException {
        StopWordsManager.getInstance().loadFromFile(path);
    }

    public static void loadArticles(String path) throws ArticleManagerException {
        ArticleManager articleManager = ArticleManager.getInstance();
        articleManager.loadFromFiles(path);
    }

    public static List<FeaturesVector> getExtractedFeatures() {
        List<FeaturesVector> featuresVector = new ArrayList<>();
        ArticleManager.getInstance().getArticles().forEach(article -> featuresVector.add(FeaturesManager.getInstance().extractFeatures(article)));
        return featuresVector;
    }
}
