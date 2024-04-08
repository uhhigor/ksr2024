package org.example.modules.featureExtraction.exceptions;

public class ArticleException extends FeatureExtractionModuleException{
    public ArticleException(String message) {
        super(message);
    }

    public ArticleException(String message, Throwable cause) {
        super(message, cause);
    }
}
