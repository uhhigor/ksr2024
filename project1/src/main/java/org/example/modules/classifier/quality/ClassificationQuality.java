package org.example.modules.classifier.quality;

import java.util.HashMap;
import java.util.List;

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

        return (double) tp / populationSize;
    }

    public double [] calculateCountryPrecision() {
        return calculateQuality(fpMap);
    }

    public double [] calculateCountryRecall() {
        return calculateQuality(fnMap);
    }

    private double[] calculateQuality(HashMap<String, Integer> qualityMap) {
        double [] quality = new double[6];
        for (int i = 0; i < 6; i++) {
            String label = tpMap.keySet().toArray()[i].toString();
            int qualitySum = 0;
            for (int j = 0; j < 6; j++) {
                if (i != j) {
                    qualitySum += qualityMap.get(qualityMap.keySet().toArray()[j].toString());
                }
            }
            quality[i] = (double) tpMap.get(label) / (tpMap.get(label) + qualitySum);
        }
        return quality;
    }

    public double[] calculateCountryF1Score() {
        double [] f1Score = new double[6];
        double [] precision = calculateCountryPrecision();
        double [] recall = calculateCountryRecall();
        for (int i = 0; i < 6; i++) {
            f1Score[i] = 2 * precision[i] * recall[i] / (precision[i] + recall[i]);
        }
        return f1Score;
    }

    public double calculateWeightedAveragePrecision() {
        double [] precision = calculateCountryPrecision();
        return calculateQualityAverage(precision);
    }

    public double calculateWeightedAverageRecall() {
        double [] recall = calculateCountryRecall();
        return calculateQualityAverage(recall);
    }

    public double calculateWeightedAverageF1Score() {
        double [] f1Score = calculateCountryF1Score();
        return calculateQualityAverage(f1Score);
    }

    private double calculateQualityAverage(double[] quality) {
        double [] real = new double[6];
        for (int i = 0; i < 6; i++) {
            real[i] = (double) realMap.get(realMap.keySet().toArray()[i].toString());
        }
        double weightedAveragePrecision = 0;
        for (int i = 0; i < 6; i++) {
            weightedAveragePrecision += quality[i] * real[i];
        }
        return weightedAveragePrecision / populationSize;
    }
}
