package live.daniel.TaskManager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Daniel on 05.12.2016.
 */
public class editTask {
    @FXML
    TextField timeActivation;
    @FXML
    TextField timeExecute;
    @FXML
    Button btnOkey;
    @FXML
    Button btnCancel;

    private Stage dialogStage;
    private boolean okClicked = false;
    private int TimeActivation, TimeExecute;

    /**
     * Set scene for this is window
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public int getTimeExecute() {
        return TimeExecute;
    }
    public void setTimeExecute(int timeExecute) {
        TimeExecute = timeExecute;
    }

    public int getTimeActivation() {
        return TimeActivation;
    }
    public void setTimeActivation(int timeActivation) {
        TimeActivation = timeActivation;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    protected void handleOk() {
        if (isInputValid()) {
            setTimeActivation(Integer.parseInt(timeActivation.getText()));
            setTimeExecute(Integer.parseInt(timeExecute.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    protected void handleCancel() {
        dialogStage.close();
    }

    /**
     * @return true, if enter correct
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (timeActivation.getText() == null || timeActivation.getText().length() == 0) {
            errorMessage += "No valid Time activation!\n";
        } else {
            // пытаемся преобразовать в int.
            try {
                Integer.parseInt(timeActivation.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Time activation (must be an integer)!\n";
            }
        }

        if (timeExecute.getText() == null || timeExecute.getText().length() == 0) {
            errorMessage += "No valid Time execute!\n";
        } else {
            // пытаемся преобразовать в int.
            try {
                Integer.parseInt(timeExecute.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Time execute (must be an integer)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Показываем сообщение об ошибке.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
