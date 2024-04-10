package org.example.modules.featureExtraction;

import org.example.modules.featureExtraction.exceptions.ArticleManagerException;
import org.example.modules.featureExtraction.exceptions.StopWordsManagerException;

import java.util.ArrayList;
import java.util.List;

public class FeatureExtractionModule {

    public FeatureExtractionModule(String stopWordsPath, String articlesPath) throws StopWordsManagerException, ArticleManagerException {
        loadStopWords(stopWordsPath);
        loadArticles(articlesPath);
    }

    private void loadStopWords(String path) throws StopWordsManagerException {
        NLPUtils.getInstance().loadStopWordsFromFile(path);
    }

    private void loadArticles(String path) throws ArticleManagerException {
        ArticleManager articleManager = ArticleManager.getInstance();
        articleManager.loadFromFiles(path);
    }

    public List<FeaturesVector> getExtractedFeatures() {
        List<FeaturesVector> featuresVector = new ArrayList<>();
        ArticleManager.getInstance().getArticles().forEach(article -> featuresVector.add(FeaturesManager.getInstance().extractFeatures(article)));
        return featuresVector;
    }
}
