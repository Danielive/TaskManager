package app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@SuppressWarnings("WeakerAccess")
public class SetProcessor {
    @SuppressWarnings({"WeakerAccess", "unused"})
    @FXML
    TextField countProcessor;
    @FXML
    Button btnSet;

    private Stage dialogStage;
    private int countP;
    private boolean okClicked = false;

    public SetProcessor() {

    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public int getCountP() {
        return countP;
    }
    @SuppressWarnings("WeakerAccess")
    public void setCountP(int countP) {
        this.countP = countP;
        okClicked = true;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @SuppressWarnings("unused")
    @FXML
    protected void setCountProcessor() {
        if (isInputValid()) {
            setCountP(Integer.parseInt(countProcessor.getText()));

            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (countProcessor.getText() == null || countProcessor.getText().length() == 0) {
            errorMessage += "No valid Count processor!\n";
        } else {
            try {
                //noinspection ResultOfMethodCallIgnored
                Integer.parseInt(countProcessor.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid processor (must be an integer)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Field");
            alert.setHeaderText("Please correct invalid field");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
