package org.example.modules.featureExtraction.exceptions;

public class ArticleManagerException extends FeatureExtractionModuleException{
    public ArticleManagerException(String message) {
        super(message);
    }

    public ArticleManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
