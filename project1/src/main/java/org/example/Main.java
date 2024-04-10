package org.example;

import org.example.modules.classifier.ClassifierModule;
import org.example.modules.classifier.exceptions.MetricException;
import org.example.modules.classifier.metric.EuklidesMetric;
import org.example.modules.featureExtraction.FeatureExtractionModule;
import org.example.modules.featureExtraction.FeaturesVector;
import org.example.modules.featureExtraction.exceptions.FeatureExtractionModuleException;

import java.util.List;

public class Main {
    public static void main(String[] args) throws FeatureExtractionModuleException, MetricException {
        final String STOP_WORDS_PATH = Main.class.getResource("/stopwords.txt").getPath();
        final String ARTICLES_PATH = Main.class.getResource("/reuters21578").getPath();
        FeatureExtractionModule featureExtractionModule = new FeatureExtractionModule(STOP_WORDS_PATH, ARTICLES_PATH);
        List<FeaturesVector> list = featureExtractionModule.getExtractedFeatures();
        System.out.println(list.size());

        ClassifierModule classifierModule = new ClassifierModule(6, list.subList(0, 500), new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, new EuklidesMetric(), 30, 70);
        classifierModule.classify();
    }
}