package org.example.modules.classifier.exceptions;

public class ClassifierModuleException extends Exception{
    public ClassifierModuleException(String message) {
        super(message);
    }

    public ClassifierModuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
