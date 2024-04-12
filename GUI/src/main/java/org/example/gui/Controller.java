package org.example.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.*;

import org.example.modules.classifier.ClassifierModule;
import org.example.modules.classifier.exceptions.MetricException;
import org.example.modules.classifier.metric.EuklidesMetric;
import org.example.modules.classifier.metric.ManhattanMetric;
import org.example.modules.classifier.metric.ChebyshevMetric;
import org.example.modules.featureExtraction.FeatureExtractionModule;
import org.example.modules.featureExtraction.FeaturesVector;
import org.example.modules.featureExtraction.exceptions.ArticleManagerException;
import org.example.modules.featureExtraction.exceptions.FeatureExtractionModuleException;
import org.example.modules.classifier.quality.ClassificationQuality;
import org.example.modules.featureExtraction.exceptions.StopWordsManagerException;


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

    private FeatureExtractionModule featureExtractionModule;
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

        final String STOP_WORDS_PATH = Objects.requireNonNull(Controller.class.getResource
                ("/stopWords.txt")).getPath();
        final String ARTICLES_PATH = Objects.requireNonNull(Application.class.getResource
                ("/reuters21578")).getPath();
        try {
            featureExtractionModule = new FeatureExtractionModule(STOP_WORDS_PATH, ARTICLES_PATH);
        } catch (StopWordsManagerException | ArticleManagerException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClassifyButton() {
        try {

            ArrayList<Integer> featuresToUse = new ArrayList<>();
            if(feature1.isSelected()) featuresToUse.add(1);
            if(feature2.isSelected()) featuresToUse.add(2);
            if(feature3.isSelected()) featuresToUse.add(3);
            if(feature4.isSelected()) featuresToUse.add(4);
            if(feature5.isSelected()) featuresToUse.add(5);
            if(feature6.isSelected()) featuresToUse.add(6);
            if(feature7.isSelected()) featuresToUse.add(7);
            if(feature8.isSelected()) featuresToUse.add(8);
            if(feature9.isSelected()) featuresToUse.add(9);
            if(feature10.isSelected()) featuresToUse.add(10);
            List<FeaturesVector> features = featureExtractionModule.getExtractedFeatures().subList(Integer.parseInt(initialParameter.getText()),
                    Integer.parseInt(endParameter.getText()));

            int[] featuresToUseArray = featuresToUse.stream().mapToInt(i -> i).toArray();

            ClassifierModule classifierModule = new ClassifierModule(Integer.parseInt(kParameter.getText()),
                    features,
                    featuresToUseArray,
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
        Map<String, Double> precision = classificationQuality.calculateCountryPrecision();
        Map<String, Double> recall = classificationQuality.calculateCountryRecall();
        Map<String, Double> f1 = classificationQuality.calculateCountryF1Score();

        accuracyLabel.setText(String.valueOf(accuracy));
        usaPrecisionLabel.setText(String.valueOf(precision.get("usa")));
        usaRecallLabel.setText(String.valueOf(recall.get("usa")));
        usaF1Label.setText(String.valueOf(f1.get("usa")));

        ukPrecisionLabel.setText(String.valueOf(precision.get("uk")));
        ukRecallLabel.setText(String.valueOf(recall.get("uk")));
        ukF1Label.setText(String.valueOf(f1.get("uk")));

        germanyPrecisionLabel.setText(String.valueOf(precision.get("west-germany")));
        germanyRecallLabel.setText(String.valueOf(recall.get("west-germany")));
        germanyF1Label.setText(String.valueOf(f1.get("west-germany")));

        canadaPrecisionLabel.setText(String.valueOf(precision.get("canada")));
        canadaRecallLabel.setText(String.valueOf(recall.get("canada")));
        canadaF1Label.setText(String.valueOf(f1.get("canada")));

        japanPrecisionLabel.setText(String.valueOf(precision.get("japan")));
        japanRecallLabel.setText(String.valueOf(recall.get("japan")));
        japanF1Label.setText(String.valueOf(f1.get("japan")));

        francePrecisionLabel.setText(String.valueOf(precision.get("france")));
        franceRecallLabel.setText(String.valueOf(recall.get("france")));
        franceF1Label.setText(String.valueOf(f1.get("france")));

        averagePrecisionLabel.setText(String.valueOf(classificationQuality.calculateWeightedAveragePrecision()));
        averageRecallLabel.setText(String.valueOf(classificationQuality.calculateWeightedAverageRecall()));
        averageF1Label.setText(String.valueOf(classificationQuality.calculateWeightedAverageF1Score()));
    }
}
