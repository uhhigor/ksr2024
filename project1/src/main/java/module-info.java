module project1 {
    opens org.example;
    exports org.example.modules.classifier;
    exports org.example.modules.classifier.exceptions;
    exports org.example.modules.classifier.metric;
    exports org.example.modules.featureExtraction;
    exports org.example.modules.featureExtraction.exceptions;
    exports org.example.modules.classifier.quality;
}