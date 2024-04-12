package org.example.modules.classifier.quality;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassificationQuality {

    private final int populationSize;
    HashMap<String, Integer> tpMap = new HashMap<>();
    HashMap<String, Integer> fpMap = new HashMap<>();
    HashMap<String, Integer> fnMap = new HashMap<>();
    HashMap<String, Integer> realMap = new HashMap<>();

    public ClassificationQuality(List<String> predicted, List<String> real) {
        if (predicted.size() != real.size()) {
            throw new IllegalArgumentException("Predicted and real lists must have the same size");
        }
        this.populationSize = predicted.size();

        createMap(predicted, real);
    }

    private void createMap(List <String> predicted, List <String> real) {
        String[] labels = {"usa", "uk", "west-germany", "canada", "japan", "france"};

        for (String label : labels) {
            tpMap.put(label, 0);
            fpMap.put(label, 0);
            fnMap.put(label, 0);
            realMap.put(label, 0);
        }

        for (int i = 0; i < predicted.size(); i++) {
            String pred = predicted.get(i);
            String realLabel = real.get(i);
            realMap.put(realLabel, realMap.get(realLabel) + 1);
            if (pred.equals(realLabel)) {
                tpMap.put(pred, tpMap.get(pred) + 1);
            } else {
                fpMap.put(pred, fpMap.get(pred) + 1);
                fnMap.put(realLabel, fnMap.get(realLabel) + 1);
            }
        }

        System.out.println("TP: " + tpMap);
        System.out.println("FP: " + fpMap);
        System.out.println("FN: " + fnMap);
        System.out.println("REAL: " + realMap);
    }

    public double calculateAccuracy() {
        int tp = 0;

        for (String label : tpMap.keySet()) {
            tp += tpMap.get(label);
        }

        return Math.round ((double) tp / populationSize * 100.0) / 100.0;
    }

    public Map<String, Double> calculateCountryPrecision() {
        return calculateQuality(fpMap);
    }

    public Map<String, Double> calculateCountryRecall() {
        return calculateQuality(fnMap);
    }

    private Map<String, Double> calculateQuality(HashMap<String, Integer> qualityMap) {
        Map<String, Double> quality = new HashMap<>();
        int qualitySum = 0;
        for(int i = 0; i < 6; i++) {
            qualitySum += qualityMap.get(qualityMap.keySet().toArray()[i].toString());
        }
        for(int i = 0; i < 6; i++) {

            String label = tpMap.keySet().toArray()[i].toString();
            double currentSum = qualitySum - qualityMap.get(label);
            quality.put(label, Math.round((double) tpMap.get(label) / ((tpMap.get(label) + currentSum)) * 100.0) / 100.0);
        }
        return quality;
    }

    public Map<String, Double> calculateCountryF1Score() {
        Map<String, Double> f1Score = new HashMap<>();
        Map<String, Double> precision = calculateCountryPrecision();
        Map<String, Double> recall = calculateCountryRecall();

        for(String label : precision.keySet()) {
            f1Score.put(label, Math.round((2 * precision.get(label) * recall.get(label) / (precision.get(label) + recall.get(label))) * 100.0) / 100.0);
        }
        return f1Score;
    }

    public double calculateWeightedAveragePrecision() {
        Map<String, Double> precision = calculateCountryPrecision();
        return calculateQualityAverage(precision);
    }

    public double calculateWeightedAverageRecall() {
        Map<String, Double> recall = calculateCountryRecall();
        return calculateQualityAverage(recall);
    }

    public double calculateWeightedAverageF1Score() {
        Map<String, Double> f1Score = calculateCountryF1Score();
        return calculateQualityAverage(f1Score);
    }

    private double calculateQualityAverage(Map<String, Double> quality) {
        Map<String, Double> real = new HashMap<>();
        for(String label : realMap.keySet()) {
            real.put(label, (double) realMap.get(label));
        }

        double weightedAveragePrecision = 0;
        for(String label : quality.keySet()) {
            weightedAveragePrecision += quality.get(label) * real.get(label);
        }
        return Math.round(weightedAveragePrecision / populationSize * 100.0) / 100.0;
    }
}
