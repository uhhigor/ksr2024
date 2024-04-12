package org.example.modules.classifier;

import org.example.modules.classifier.exceptions.MetricException;
import org.example.modules.classifier.metric.Metric;
import org.example.modules.featureExtraction.FeaturesVector;

import java.util.*;

public class ClassifierModule {
    private final int k;
    private final List<FeaturesVector> featuresVectors;
    private final int[] featuresToUse;
    private final Metric metric;
    private final int trainSizePercent;
    private final int testSizePercent;
    private final List<String> predicted = new ArrayList<>();
    private final List<String> real = new ArrayList<>();

    public ClassifierModule(int k, List<FeaturesVector> featuresVectors, int[] featuresToUse, Metric metric, int trainSizePercent, int testSizePercent) {
        this.k = k;
        this.featuresVectors = featuresVectors;
        this.featuresToUse = featuresToUse;
        this.metric = metric;
        this.trainSizePercent = trainSizePercent;
        this.testSizePercent = testSizePercent;
        if(trainSizePercent + testSizePercent != 100){
            throw new IllegalArgumentException("Sum of trainSizePercent and testSizePercent must be equal to 100");
        }
    }

    public List<String> getPredicted() {
        return predicted;
    }

    public List<String> getReal() {
        return real;
    }

    public record VectorDistance(FeaturesVector vector, double distance){}

    public void classify() throws MetricException {
        int trainSize = featuresVectors.size() * trainSizePercent / 100;
        int testSize = featuresVectors.size() * testSizePercent / 100;

        List<FeaturesVector> trainVectors = featuresVectors.subList(0, trainSize);
        List<FeaturesVector> testVectors = featuresVectors.subList(trainSize, trainSize + testSize);

        for(FeaturesVector testVector : testVectors) {
            List<VectorDistance> distances = new ArrayList<>();
            for(FeaturesVector trainVector : trainVectors) {
                double distance = metric.calculateDistance(testVector, trainVector, featuresToUse);
                distances.add(new VectorDistance(trainVector, distance));
            }
            distances.sort(Comparator.comparingDouble(VectorDistance::distance));
            for(VectorDistance distance : distances) {
                System.out.print(distance.vector.getCountry() + " " + distance.distance() + ", ");
            }
            System.out.println();
            HashMap<String, Integer> votes = new HashMap<>();
            for(int i = 0; i < k; i++) {
                String country = distances.get(i).vector.getCountry();
                if(votes.containsKey(country)) {
                    votes.put(country, votes.get(country) + 1);
                } else {
                    votes.put(country, 1);
                }
            }
            int maxVotes = 0;
            String predictedCountry = "";
            for(String country : votes.keySet()) {
                if(votes.get(country) > maxVotes) {
                    maxVotes = votes.get(country);
                    predictedCountry = country;
                }
            }
            predicted.add(predictedCountry);
            real.add(testVector.getCountry());
        }
    }
}
