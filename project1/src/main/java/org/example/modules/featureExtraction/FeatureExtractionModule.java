package org.example.modules.featureExtraction;

import org.example.modules.featureExtraction.exceptions.ArticleManagerException;
import org.example.modules.featureExtraction.exceptions.FeatureExtractionModuleException;
import org.example.modules.featureExtraction.exceptions.FeaturesVectorException;
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

    public List<FeaturesVector> getExtractedFeatures() throws FeatureExtractionModuleException {
        List<FeaturesVector> featuresVectorList = new ArrayList<>();
        List<Article> articles = ArticleManager.getInstance().getArticles();
        for (Article article : articles) {
            try {
                featuresVectorList.add(FeaturesManager.getInstance().extractFeatures(article));
            } catch (FeaturesVectorException e) {
                throw new FeatureExtractionModuleException("Error while extracting features", e);
            }
        }
        return featuresVectorList;
    }
}
