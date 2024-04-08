package org.example.modules.featureExtraction.exceptions;

public class FeatureExtractionModuleException extends Exception{
    public FeatureExtractionModuleException(String message) {
        super(message);
    }

    public FeatureExtractionModuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
