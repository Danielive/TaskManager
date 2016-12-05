package live.daniel.TaskManager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import live.daniel.TaskManager.CollectionTasks;
import live.daniel.TaskManager.Manager;
import live.daniel.TaskManager.Manager.*;
import live.daniel.TaskManager.Task;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by Daniel on 24.11.2016.
 */
public class mainForm {
    private CollectionTasks collectionTasks = new CollectionTasks();

    @FXML
    protected Label countTasks;
    @FXML
    protected TextField tfl;
    @FXML
    protected Button addTask;
    @FXML
    protected Button delTask;
    @FXML
    protected TextField sizeProc;
    @FXML
    protected TreeView<String> listTasks;

    @FXML
    protected TableView tableTasks;
    @FXML
    protected TableColumn<Task, String> nameTask;
    @FXML
    protected TableColumn<Task, Integer> priorityTask;
    @FXML
    protected TableColumn<Task, Integer> timeActivationTask;
    @FXML
    protected TableColumn<Task, Integer> timeExecuteTask;
    @FXML
    protected TableColumn<Task, Boolean> execute;

    TreeItem<String> item;

    Image icon = new Image(getClass().getResourceAsStream("/img/icon.ico"));

    //It is checked method getName(), PropertyValueFactory - "Name"
    @FXML
    protected void initialize() {
        nameTask.setCellValueFactory(new PropertyValueFactory<>("Name"));
        priorityTask.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        timeActivationTask.setCellValueFactory(new PropertyValueFactory<>("TimeActivation"));
        timeExecuteTask.setCellValueFactory(new PropertyValueFactory<>("TimeUsing"));
        execute.setCellValueFactory(new PropertyValueFactory<>("Using"));
        tableTasks.setItems(CollectionTasks.getTasks());

        //Для выбора нескольких записей
        //tableTasks.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //tableTasks.setEditable(true); //Для редактирования

        //Срабатывает при изменении. с - подсказывает что изменено
        collectionTasks.getTasks().addListener((ListChangeListener<Task>) c -> {
            updateCountTasks();
        });
    }

    protected void updateCountTasks() {
        countTasks.setText("Count tasks: " + CollectionTasks.getTasks().size());
    }

    @FXML
    protected void setTreeView() {
        TreeItem<String> root = new TreeItem<>("Tasks", new ImageView(icon));
        root.setExpanded(true);

        File rootTasks = new File("D:\\Projects\\IdeaProjects\\TaskManager\\src\\main\\resources\\tasks");
        ArrayList<File> al = new ArrayList<File>();
        File[] files = rootTasks.listFiles();

        for (File singleFile : files) {
            root.getChildren().add(new TreeItem<>(singleFile.getName()));
            al.add(singleFile);
            if(singleFile.getName().endsWith(".exe")) {

            }
        }
        listTasks.setRoot(root);
    }

    @FXML
    protected void clickMouseTreeView(MouseEvent event) {
        item = listTasks.getSelectionModel().getSelectedItem();
    }

    @FXML
    protected void setAddTask() {
        if (item.getValue() != "") {
            if (item.getValue() == "Tasks") return;
            else if (item.getValue().endsWith(".exe"))
                CollectionTasks.getTasks().add(new Task(item.getValue(), 1, 1, 2, false));
            else if (item.getValue().endsWith(".txt"))
                CollectionTasks.getTasks().add(new Task(item.getValue(), 2, 1, 2, false));
            else if (item.getValue().endsWith(".mp3"))
                CollectionTasks.getTasks().add(new Task(item.getValue(), 3, 1, 2, false));
            else if (item.getValue().endsWith(".jpeg"))
                CollectionTasks.getTasks().add(new Task(item.getValue(), 4, 1, 2, false));
            else CollectionTasks.getTasks().add(new Task(item.getValue() + " default", 4, 1, 2, false));
        }
    }
    /*
    @FXML
    protected void setAddTask(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        //If not clicked - return
        if (!(source instanceof Button)) return;
        Button clickedButton = (Button) source;
        Task selectedTask = (Task) tableTasks.getSelectionModel().getSelectedItem();

        if (item.getValue() != "") {
            if (item.getValue() == "Tasks") return;
            else if (item.getValue().endsWith(".exe"))
                CollectionTasks.getTasks().add(new Task(item.getValue(), 1, 1, 2, false));
            else if (item.getValue().endsWith(".txt"))
                CollectionTasks.getTasks().add(new Task(item.getValue(), 2, 1, 2, false));
            else if (item.getValue().endsWith(".mp3"))
                CollectionTasks.getTasks().add(new Task(item.getValue(), 3, 1, 2, false));
            else if (item.getValue().endsWith(".jpeg"))
                CollectionTasks.getTasks().add(new Task(item.getValue(), 4, 1, 2, false));
            else CollectionTasks.getTasks().add(new Task(item.getValue() + " default", 4, 1, 2, false));
        }

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/editTask.fxml"));
            Parent content = loader.load();
            stage.setTitle("Add task");
            stage.setMinHeight(150);
            stage.setMinWidth(300);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
*/
    @FXML
    protected void setDelTask() {
        Task selectedTask = (Task) tableTasks.getSelectionModel().getSelectedItem();
        collectionTasks.getTasks().remove(selectedTask);
    }

    @FXML
    protected int setSizeProc() {
        String sizeS = sizeProc.getText();
        int sizeInt = Integer.parseInt(sizeS);
        countTasks.setText(sizeS);
        return sizeInt;
    }

    @FXML
    protected String getSizeProc() {
        return sizeProc.getText();
    }

    //Указкать проц в Manager - Executors.newFixedThreadPool(getSP())
    protected int getSP() {
        int size = Integer.parseInt(sizeProc.getText());
        return size;
    }

    @FXML
    protected void startProcess() throws InterruptedException {
        Manager m = new Manager();
        m.execute(3);
    }
}
