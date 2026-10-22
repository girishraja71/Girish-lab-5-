package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import model.UserValidator;

/**
 * CONTROLLER: the middleman between the View (UserForm.fxml) and the Model.
 * 1. Reads what the user typed from the View.
 * 2. Asks the Model (UserValidator) whether it is valid.
 * 3. Either shows the errors, or builds a User and shows it in a pop-up.
 */
public class UserFormController {

    // Each @FXML field matches an fx:id in UserForm.fxml; FXMLLoader fills them in.
    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField emailField;
    @FXML private Label nameError;
    @FXML private Label ageError;
    @FXML private Label emailError;

    /** Called automatically by FXMLLoader after the @FXML fields are set. */
    @FXML
    private void initialize() {
        // Live validation: re-check a field every time its text changes,
        // but only after the user has already seen an error for it.
        nameField.textProperty().addListener((obs, oldV, newV) -> {
            if (!nameError.getText().isEmpty()) validateName();
        });
        ageField.textProperty().addListener((obs, oldV, newV) -> {
            if (!ageError.getText().isEmpty()) validateAge();
        });
        emailField.textProperty().addListener((obs, oldV, newV) -> {
            if (!emailError.getText().isEmpty()) validateEmail();
        });

        // Block anything other than digits being typed into Age (max 3 digits).
        ageField.setTextFormatter(new javafx.scene.control.TextFormatter<String>(change ->
                change.getControlNewText().matches("\\d{0,3}") ? change : null));
    }

    /** Runs when Submit is clicked (onAction="#handleSubmit" in the FXML). */
    @FXML
    private void handleSubmit() {
        // Use & (not &&) so every field is checked and every error is shown at once.
        boolean valid = validateName() & validateAge() & validateEmail();

        if (!valid) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input",
                    "Please fix the highlighted fields.", collectErrors());
            return;
        }

        User user = new User(
                nameField.getText().trim(),
                Integer.parseInt(ageField.getText().trim()),
                emailField.getText().trim());

        showAlert(Alert.AlertType.INFORMATION, "Details Submitted",
                "Thank you! Here are the details you entered:", user.toString());

        // showAndWait() above blocks until the pop-up is closed, then the form is reset.
        handleClear();
    }

    /** Runs when Clear is clicked. */
    @FXML
    private void handleClear() {
        nameField.clear();
        ageField.clear();
        emailField.clear();
        setError(nameField, nameError, null);
        setError(ageField, ageError, null);
        setError(emailField, emailError, null);
        nameField.requestFocus();
    }

    private boolean validateName() {
        return setError(nameField, nameError, UserValidator.validateName(nameField.getText()));
    }

    private boolean validateAge() {
        return setError(ageField, ageError, UserValidator.validateAge(ageField.getText()));
    }

    private boolean validateEmail() {
        return setError(emailField, emailError, UserValidator.validateEmail(emailField.getText()));
    }

    /** Shows or hides the error under a field and the red border. Returns true when valid. */
    private boolean setError(TextField field, Label errorLabel, String message) {
        if (message == null) {
            errorLabel.setText("");
            field.getStyleClass().remove("invalid");
            return true;
        }
        errorLabel.setText(message);
        if (!field.getStyleClass().contains("invalid")) {
            field.getStyleClass().add("invalid");
        }
        return false;
    }

    private String collectErrors() {
        StringBuilder sb = new StringBuilder();
        for (Label l : new Label[] {nameError, ageError, emailError}) {
            if (!l.getText().isEmpty()) {
                sb.append("• ").append(l.getText()).append("\n");
            }
        }
        return sb.toString().trim();
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
