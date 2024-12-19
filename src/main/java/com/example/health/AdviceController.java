package com.example.health;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Vector;

public class AdviceController {

    @FXML
    private Label adviceLabel;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button getBackButton, generateAdvice;

    private Vector<Diseases> diseases;

    private Patient patient;

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @FXML
    private void initialize() {
        diseases.add(Diseases.DIABETES);
        diseases.add(Diseases.HEART_DISEASE);
        patient = new Patient(
                "John",   // First name
                "Doe",    // Surname
                45.0,     // Age
                175.0,    // Height in cm
                72,       // Heart rate
                80.0,     // Weight in kg
                Gender.Male, // Gender
                diseases  // Chronic diseases
        );
        generateAdvice();
    }

    private void generateAdvice() {
        StringBuilder advice = new StringBuilder();
        advice.append("Welcome, ").append(patient.getName() + " " + patient.getSurname() + "!\n\n");
        advice.append("BMI Analysis: ").append(patient.getBMIAnalysis()).append("\n");
        advice.append("Body Fat Percentage: ").append(String.format("%.2f%%", patient.calculateBFP())).append("\n");
        advice.append("Heart Rate Analysis: ").append(patient.getHeartRateAnalysis()).append("\n");
        advice.append("Daily Water Intake: ").append(String.format("%.2f liters", patient.calculateWaterIntake())).append(" (or ")
                .append(String.format("%.0f cups", patient.calculateWaterCups())).append(")\n\n");

        // Ensure diseases is properly populated
        if (patient.getDiseases() != null && !patient.getDiseases().isEmpty()) {
            advice.append("Chronic Diseases: \n");
            for (Diseases disease : patient.getDiseases()) {
                advice.append("- ").append(disease.name()).append("\n");
            }
        }

        adviceLabel.setText(advice.toString());
    }


    @FXML
    private void getBack(ActionEvent actionEvent) throws IOException {
        Parent parent;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/health/hello-view.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Something went wrong");
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Registration");
        stage.centerOnScreen();
    }

    private void showErrorDialog(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(text);
        alert.showAndWait();
    }

}
