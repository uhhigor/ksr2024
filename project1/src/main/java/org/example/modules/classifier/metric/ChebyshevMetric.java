package org.example.modules.classifier.metric;

import org.example.modules.classifier.exceptions.MetricException;
import org.example.modules.featureExtraction.FeaturesVector;

public class ChebyshevMetric implements Metric {

    @Override
    public double calculateDistance(FeaturesVector vector1, FeaturesVector vector2, int[] featuresToUse) throws MetricException {
        double max = 0;
        for (int featureNumber : featuresToUse) {
            Object featureValue1 = vector1.get(featureNumber);
            Object featureValue2 = vector2.get(featureNumber);
            switch (featureValue1) {
                case Double value1 when featureValue2 instanceof Double value2 -> {
                    double diff = Math.abs(value1 - value2);
                    if (diff > max) {
                        max = diff;
                    }
                }
                case Boolean value1 when featureValue2 instanceof Boolean value2 -> {
                    int value1Int = value1 ? 1 : 0;
                    int value2Int = value2 ? 1 : 0;
                    double diff = Math.abs(value1Int - value2Int);
                    if (diff > max) {
                        max = diff;
                    }
                }
                case String value1 when featureValue2 instanceof String value2 -> {
                    double diff = StringDistance.calculateDistance(value1, value2);
                    if (diff > max) {
                        max = diff;
                    }
                }
                default -> throw new MetricException("Unsupported feature type: " + featureValue1.getClass().getSimpleName() + " or " + featureValue2.getClass().getSimpleName());
            }
        }
        return max;
    }
}
