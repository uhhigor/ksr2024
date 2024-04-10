package org.example;

import org.example.modules.classifier.ClassifierModule;
import org.example.modules.classifier.exceptions.MetricException;
import org.example.modules.classifier.metric.EuklidesMetric;
import org.example.modules.featureExtraction.FeatureExtractionModule;
import org.example.modules.featureExtraction.FeaturesVector;
import org.example.modules.featureExtraction.exceptions.FeatureExtractionModuleException;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FeatureExtractionModuleException, MetricException {

        final String STOP_WORDS_PATH = Main.class.getResource("/stopwords.txt").getPath().substring(1);
        final String ARTICLES_PATH = Main.class.getResource("/reuters21578").getPath().substring(1);
        System.out.println(STOP_WORDS_PATH);
        System.out.println(ARTICLES_PATH);
        FeatureExtractionModule featureExtractionModule = new FeatureExtractionModule(STOP_WORDS_PATH, ARTICLES_PATH);
        List<FeaturesVector> list = featureExtractionModule.getExtractedFeatures();
//        System.out.println(list.size());
//        list.forEach(System.out::println);

        ClassifierModule classifierModule = new ClassifierModule(5, list.subList(0, 1000), new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, new EuklidesMetric(), 50, 50);
        classifierModule.classify();
    }
}