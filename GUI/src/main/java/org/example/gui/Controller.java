package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import org.example.modules.classifier.ClassifierModule;
import org.example.modules.classifier.exceptions.MetricException;
import org.example.modules.classifier.metric.EuklidesMetric;
import org.example.modules.classifier.metric.ManhattanMetric;
import org.example.modules.classifier.metric.ChebyshevMetric;
import org.example.modules.featureExtraction.FeatureExtractionModule;
import org.example.modules.featureExtraction.exceptions.FeatureExtractionModuleException;
import org.example.modules.classifier.quality.ClassificationQuality;


public class Controller implements Initializable {

    @FXML
    private TextField kParameter, testParameter, trainParameter,initialParameter, endParameter;

    @FXML
    private CheckBox feature1, feature2, feature3, feature4, feature5, feature6, feature7, feature8, feature9, feature10;

    @FXML
    private ChoiceBox<String> metricChoiceBox;

    @FXML
    private Button btnClassify;

    @FXML
    private Label usaPrecisionLabel, usaRecallLabel, usaF1Label;

    @FXML
    private Label ukPrecisionLabel, ukRecallLabel, ukF1Label;

    @FXML
    private Label japanPrecisionLabel, japanRecallLabel, japanF1Label;

    @FXML
    private Label germanyPrecisionLabel, germanyRecallLabel, germanyF1Label;

    @FXML
    private Label canadaPrecisionLabel, canadaRecallLabel, canadaF1Label;

    @FXML
    private Label francePrecisionLabel, franceRecallLabel, franceF1Label;

    @FXML
    private Label accuracyLabel, averagePrecisionLabel, averageRecallLabel, averageF1Label;

    private final String[] metrics = {"Euclides", "Manhattan", "Chebyshev"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        kParameter.setText("5");
        testParameter.setText("50");
        trainParameter.setText("50");
        initialParameter.setText("0");
        endParameter.setText("1000");

        metricChoiceBox.getItems().addAll(metrics);
        metricChoiceBox.setValue(metrics[0]);

        feature1.setSelected(true);
        feature2.setSelected(true);
        feature3.setSelected(true);
        feature4.setSelected(true);
        feature5.setSelected(true);
        feature6.setSelected(true);
        feature7.setSelected(true);
        feature8.setSelected(true);
        feature9.setSelected(true);
        feature10.setSelected(true);

        btnClassify.setOnAction(event -> onClassifyButton());
    }

    public void onClassifyButton() {
        try {
            final String STOP_WORDS_PATH = Objects.requireNonNull(Controller.class.getResource
                    ("/stopWords.txt")).getPath().substring(1);
            final String ARTICLES_PATH = Objects.requireNonNull(Controller.class.getResource
                    ("/reuters21578")).getPath().substring(1);
            FeatureExtractionModule featureExtractionModule = new FeatureExtractionModule(STOP_WORDS_PATH, ARTICLES_PATH);
            ClassifierModule classifierModule = new ClassifierModule(Integer.parseInt(kParameter.getText()),
                    featureExtractionModule.getExtractedFeatures().subList(Integer.parseInt(initialParameter.getText()),
                            Integer.parseInt(endParameter.getText())),
                    new int[]{feature1.isSelected() ? 1 : 0, feature2.isSelected() ? 2 : 0, feature3.isSelected() ? 3 : 0,
                            feature4.isSelected() ? 4 : 0, feature5.isSelected() ? 5 : 0, feature6.isSelected() ? 6 : 0,
                            feature7.isSelected() ? 7 : 0, feature8.isSelected() ? 8 : 0, feature9.isSelected() ? 9 : 0,
                            feature10.isSelected() ? 10 : 0},
                    switch (metricChoiceBox.getValue()) {
                        case "Euclides" -> new EuklidesMetric();
                        case "Manhattan" -> new ManhattanMetric();
                        case "Chebyshev" -> new ChebyshevMetric();
                        default -> throw new IllegalArgumentException("Unsupported metric");
                    },
                    Integer.parseInt(trainParameter.getText()), Integer.parseInt(testParameter.getText()));
            classifierModule.classify();

            setQualityLabels(classifierModule.getPredicted(), classifierModule.getReal());

        } catch (FeatureExtractionModuleException | MetricException e) {
               e.printStackTrace();
        }
    }

    public void setQualityLabels(List<String> predicted, List<String> real)
    {
        ClassificationQuality classificationQuality = new ClassificationQuality(predicted, real);
        double accuracy = classificationQuality.calculateAccuracy();
        double[] precision = classificationQuality.calculateCountryPrecision();
        double[] recall = classificationQuality.calculateCountryRecall();
        double[] f1 = classificationQuality.calculateCountryF1Score();

        accuracyLabel.setText(String.valueOf(accuracy));
        usaPrecisionLabel.setText(String.valueOf(precision[0]));
        usaRecallLabel.setText(String.valueOf(recall[0]));
        usaF1Label.setText(String.valueOf(f1[0]));

        ukPrecisionLabel.setText(String.valueOf(precision[1]));
        ukRecallLabel.setText(String.valueOf(recall[1]));
        ukF1Label.setText(String.valueOf(f1[1]));

        germanyPrecisionLabel.setText(String.valueOf(precision[2]));
        germanyRecallLabel.setText(String.valueOf(recall[2]));
        germanyF1Label.setText(String.valueOf(f1[2]));

        canadaPrecisionLabel.setText(String.valueOf(precision[3]));
        canadaRecallLabel.setText(String.valueOf(recall[3]));
        canadaF1Label.setText(String.valueOf(f1[3]));

        japanPrecisionLabel.setText(String.valueOf(precision[4]));
        japanRecallLabel.setText(String.valueOf(recall[4]));
        japanF1Label.setText(String.valueOf(f1[4]));

        francePrecisionLabel.setText(String.valueOf(precision[5]));
        franceRecallLabel.setText(String.valueOf(recall[5]));
        franceF1Label.setText(String.valueOf(f1[5]));

        averagePrecisionLabel.setText(String.valueOf(classificationQuality.calculateWeightedAveragePrecision()));
        averageRecallLabel.setText(String.valueOf(classificationQuality.calculateWeightedAverageRecall()));
        averageF1Label.setText(String.valueOf(classificationQuality.calculateWeightedAverageF1Score()));
    }
}
