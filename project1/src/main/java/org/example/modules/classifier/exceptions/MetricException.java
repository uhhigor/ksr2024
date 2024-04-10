package org.example.modules.classifier.exceptions;

public class MetricException extends ClassifierModuleException{
    public MetricException(String message) {
        super(message);
    }

    public MetricException(String message, Throwable cause) {
        super(message, cause);
    }
}
