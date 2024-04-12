package org.example.modules.featureExtraction;

import org.example.modules.featureExtraction.exceptions.FeaturesVectorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeaturesVector {

    private String country;
    private final Object[] features = new Object[10];

    public FeaturesVector() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String toString() {
        return "FeaturesVector{" +
                "country='" + country + '\'' +
                ", features=" + Arrays.toString(features) +
                '}';
    }

    public void normalize() {
        List<Double> doubleList = new ArrayList<>();

        for(Object feature : features) {
            if(feature instanceof Double) {
                doubleList.add((Double) feature);
            }
        }

        double max = doubleList.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double min = doubleList.stream().mapToDouble(Double::doubleValue).min().orElse(0);

        for(int i = 0; i < features.length; i++) {
            if(features[i] instanceof Double) {
                features[i] = ((Double) features[i] - min) / (max - min);
            }
        }
    }

    public Object get(int i) {
        return switch (i) {
            case 1 -> features[0];
            case 2 -> features[1];
            case 3 -> features[2];
            case 4 -> features[3];
            case 5 -> features[4];
            case 6 -> features[5];
            case 7 -> features[6];
            case 8 -> features[7];
            case 9 -> features[8];
            case 10 -> features[9];
            default -> null;
        };
    }

    public void set(int i, Object value) throws FeaturesVectorException {
        if(value instanceof Double || value instanceof Boolean || value instanceof String) {
            features[i - 1] = value;
        } else {
            throw new FeaturesVectorException("Unsupported feature type: " + value.getClass().getSimpleName());
        }
    }
}
