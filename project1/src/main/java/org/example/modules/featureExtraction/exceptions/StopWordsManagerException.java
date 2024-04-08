package org.example.modules.featureExtraction.exceptions;

public class StopWordsManagerException extends FeatureExtractionModuleException{
    public StopWordsManagerException(String message) {
        super(message);
    }

    public StopWordsManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
