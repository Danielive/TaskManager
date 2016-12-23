package live.daniel.TaskManager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@SuppressWarnings("WeakerAccess")
public class addTask {
    @FXML
    TextField timeActivation;
    @FXML
    TextField timeExecute;
    @SuppressWarnings("unused")
    @FXML
    TextField priorityTask;
    @FXML
    Button btnOkey;
    @FXML
    Button btnCancel;

    private Stage dialogStage;
    private boolean okClicked = false;
    private int TimeActivation, TimeExecute, PriorityTask;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public int getTimeExecute() {
        return TimeExecute;
    }
    @SuppressWarnings("WeakerAccess")
    public void setTimeExecute(int timeExecute) {
        TimeExecute = timeExecute;
    }

    public int getTimeActivation() {
        return TimeActivation;
    }
    @SuppressWarnings("WeakerAccess")
    public void setTimeActivation(int timeActivation) {
        TimeActivation = timeActivation;
    }

    public int getPriorityTask() {
        return PriorityTask;
    }
    @SuppressWarnings("WeakerAccess")
    public void setPriorityTask(int priorityTask) {
        PriorityTask = priorityTask;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @SuppressWarnings("unused")
    @FXML
    protected void handleOk() {
        if (isInputValid()) {
            setTimeActivation(Integer.parseInt(timeActivation.getText()));
            setTimeExecute(Integer.parseInt(timeExecute.getText()));
            setPriorityTask(Integer.parseInt(priorityTask.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    @SuppressWarnings("unused")
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
            try {
                //noinspection ResultOfMethodCallIgnored
                Integer.parseInt(timeActivation.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Time activation (must be an integer)!\n";
            }
        }

        if (timeExecute.getText() == null || timeExecute.getText().length() == 0) {
            errorMessage += "No valid Time execute!\n";
        } else {
            try {
                //noinspection ResultOfMethodCallIgnored
                Integer.parseInt(timeExecute.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Time execute (must be an integer)!\n";
            }
        }

        if (priorityTask.getText().length() == 0 || priorityTask.getText() == null) {
            errorMessage += "No valid Time activation!\n";
        } else {
            try {
                //noinspection ResultOfMethodCallIgnored
                Integer.parseInt(priorityTask.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Time activation (must be an integer)!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
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
