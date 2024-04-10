package org.example.modules.featureExtraction.exceptions;

public class FeaturesVectorException extends FeatureExtractionModuleException{
    public FeaturesVectorException(String message) {
        super(message);
    }

    public FeaturesVectorException(String message, Throwable cause) {
        super(message, cause);
    }
}
