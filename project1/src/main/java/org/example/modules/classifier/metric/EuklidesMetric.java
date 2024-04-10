package org.example.modules.classifier.metric;

import org.example.modules.classifier.exceptions.MetricException;
import org.example.modules.featureExtraction.FeaturesVector;

public class EuklidesMetric implements Metric{
    @Override
    public double calculateDistance(FeaturesVector vector1, FeaturesVector vector2, int[] featuresToUse) throws MetricException {
        double sum = 0;
        for (int featureNumber : featuresToUse) {
            Object featureValue1 = vector1.get(featureNumber);
            Object featureValue2 = vector2.get(featureNumber);
            if(featureValue1 == null || featureValue2 == null) {
                continue;
            }
            switch (featureValue1) {
                case Double value1 when featureValue2 instanceof Double value2 -> sum += Math.pow(value1 - value2, 2);
                case Boolean value1 when featureValue2 instanceof Boolean value2 -> {
                    int value1Int = value1 ? 1 : 0;
                    int value2Int = value2 ? 1 : 0;
                    sum += Math.pow(value1Int - value2Int, 2);
                }
                case String value1 when featureValue2 instanceof String value2 -> {
                    sum += StringDistance.calculateDistance(value1, value2);
                }
                default -> throw new MetricException("Unsupported feature type: " + featureValue1.getClass().getSimpleName() + " or " + featureValue2.getClass().getSimpleName());
            }
        }
        return Math.sqrt(sum);
    }
}
