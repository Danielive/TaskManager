package live.daniel.TaskManager.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import live.daniel.TaskManager.Task;

import java.awt.event.ActionEvent;

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

    private Task task;

    public void actionCancel(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setTask(Task task) {
        this.task = task;
        timeActivation.setText(Integer.toString(task.getTimeActivation()));
        timeExecute.setText(Integer.toString(task.getTimeUsing()));
    }
}
