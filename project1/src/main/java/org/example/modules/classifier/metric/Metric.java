package org.example.modules.classifier.metric;

import org.example.modules.classifier.exceptions.MetricException;
import org.example.modules.featureExtraction.FeaturesVector;

public interface Metric {
    double calculateDistance(FeaturesVector vector1, FeaturesVector vector2, int[] featuresToUse) throws MetricException;
}
