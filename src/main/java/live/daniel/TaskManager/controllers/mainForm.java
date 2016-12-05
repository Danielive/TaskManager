package live.daniel.TaskManager.controllers;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import live.daniel.TaskManager.CollectionTasks;
import live.daniel.TaskManager.Manager;
import live.daniel.TaskManager.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Daniel on 24.11.2016.
 */
public class mainForm {
    private CollectionTasks collectionTasks = new CollectionTasks();

    @FXML
    protected Label countTasks;
    @FXML
    protected Button addTask;
    @FXML
    protected Button delTask;
    @FXML
    protected TreeView<String> listTasks;
    @FXML
    protected Label countProcessor;

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
        if (item.getValue() == "Tasks") return;
        else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(mainForm.class.getResource("/fxml/editTask.fxml"));
                Parent content = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Add task");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(content);
                dialogStage.setScene(scene);
                editTask controller = loader.getController();
                controller.setDialogStage(dialogStage);
                dialogStage.setResizable(false);
                dialogStage.showAndWait();

                if (controller.isOkClicked()) {
                    if (item.getValue().endsWith(".exe"))
                        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size()+ 1 + "_" + item.getValue(), 1, controller.getTimeActivation(), controller.getTimeExecute(), false));
                    else if (item.getValue().endsWith(".txt"))
                        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size()+ 1 + "_" + item.getValue(), 2, controller.getTimeActivation(), controller.getTimeExecute(), false));
                    else if (item.getValue().endsWith(".mp3"))
                        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size()+ 1 + "_" + item.getValue(), 3, controller.getTimeActivation(), controller.getTimeExecute(), false));
                    else if (item.getValue().endsWith(".jpeg"))
                        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size()+ 1 + "_" + item.getValue(), 4, controller.getTimeActivation(), controller.getTimeExecute(), false));
                    else
                        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size()+ 1 + "_" + item.getValue() + " default", 4, controller.getTimeActivation(), controller.getTimeExecute(), false));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void setDelTask() {
        String temp;
        Task selectedTask = (Task) tableTasks.getSelectionModel().getSelectedItem();
        collectionTasks.getTasks().remove(selectedTask);
        for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
            temp = collectionTasks.getTasks().get(i).getName().replaceAll("\\d_", "");
            collectionTasks.getTasks().get(i).setName(temp);
            collectionTasks.getTasks().get(i).setName(i+1 + "_" + collectionTasks.getTasks().get(i).getName());
        }
    }

    @FXML
    protected void exitProgram() {
        Platform.exit();
    }
/*
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
*/
    @FXML
    protected void startProgram() throws InterruptedException {
        Manager m = new Manager();
        m.execute(CollectionTasks.getTasks().size());
    }
}
