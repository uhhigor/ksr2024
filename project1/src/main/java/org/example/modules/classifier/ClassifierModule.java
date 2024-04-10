package org.example.modules.classifier;

import org.example.modules.classifier.exceptions.MetricException;
import org.example.modules.classifier.metric.Metric;
import org.example.modules.featureExtraction.FeaturesVector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ClassifierModule {
    private final int k;
    private final List<FeaturesVector> featuresVectors;
    private final int[] featuresToUse;
    private final Metric metric;

    private final int trainSizePercent;

    private final int testSizePercent;

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

    public record VectorDistance(FeaturesVector vector, double distance){}

    public void classify() throws MetricException {
        int trainSize = featuresVectors.size() * trainSizePercent / 100;
        int testSize = featuresVectors.size() * testSizePercent / 100;

        List<FeaturesVector> trainVectors = featuresVectors.subList(0, trainSize);
        List<FeaturesVector> testVectors = featuresVectors.subList(trainSize, trainSize + testSize);

        List<VectorDistance> distances = new ArrayList<>();
        for(FeaturesVector testVector : testVectors) {
            for(FeaturesVector trainVector : trainVectors) {
                distances.add(new VectorDistance(trainVector, metric.calculateDistance(testVector, trainVector, featuresToUse)));
            }
            distances.sort(Comparator.comparingDouble(VectorDistance::distance));
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
            String predictedCountry = null;
            for(String country : votes.keySet()) {
                if(votes.get(country) > maxVotes) {
                    maxVotes = votes.get(country);
                    predictedCountry = country;
                }
            }
            System.out.print(votes + " ");
            System.out.println("Predicted: " + predictedCountry + " Real: " + testVector.getCountry());
        }
    }


}
