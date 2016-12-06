package live.daniel.TaskManager.controllers;

import com.oracle.deploy.update.Updater;
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
import java.util.Timer;

/**
 * Created by Daniel on 24.11.2016.
 */
public class mainForm {
    private static CollectionTasks collectionTasks = new CollectionTasks();

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
    protected Label timeMain;

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

    static int countP = 1;

    TreeItem<String> item;

    Image icon = new Image(getClass().getResourceAsStream("/img/icon.ico"));

    //It is checked method getName(), PropertyValueFactory - "Name"
    @FXML
    protected void initialize() {
        nameTask.setCellValueFactory(new PropertyValueFactory<>("name"));
        priorityTask.setCellValueFactory(new PropertyValueFactory<>("priority"));
        timeActivationTask.setCellValueFactory(new PropertyValueFactory<>("timeActivation"));
        timeExecuteTask.setCellValueFactory(new PropertyValueFactory<>("timeUsing"));
        execute.setCellValueFactory(new PropertyValueFactory<>("using"));
        tableTasks.setItems(CollectionTasks.getTasks());

        //tableTasks.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //Для выбора нескольких записей
        //tableTasks.setEditable(true); //Для редактирования

        //Срабатывает при изменении. с - подсказывает что изменено
        collectionTasks.getTasks().addListener((ListChangeListener<Task>) c -> {
            updateCountTasks();
        });
    }

    protected void updateCountTasks() {
        countTasks.setText("Count tasks: " + CollectionTasks.getTasks().size());
    }
    protected void updateCountProcessor() {
        countProcessor.setText("Count processor: " + countP);
    }

    protected int getCountP() {
        return countP;
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
        }
        listTasks.setRoot(root);
    }

    @FXML
    protected void clickMouseTreeView() {
        item = listTasks.getSelectionModel().getSelectedItem();
    }

    @FXML
    protected void setAddTask() {
        if (item.getValue() == "Tasks") return;
        else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(mainForm.class.getResource("/fxml/addTask.fxml"));
                Parent content = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Add task");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(content);
                dialogStage.setScene(scene);
                live.daniel.TaskManager.controllers.addTask controller = loader.getController();
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

    @FXML
    protected void setCountProcessor() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(mainForm.class.getResource("/fxml/setProcessor.fxml"));
            Parent content = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Count processor");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(content);
            dialogStage.setScene(scene);
            live.daniel.TaskManager.controllers.setProcessor controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.setResizable(false);
            dialogStage.showAndWait();

            if (controller.isOkClicked()) {
                countP = controller.getCountP();
                updateCountProcessor();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void startProgram() throws InterruptedException {
        Manager m = new Manager();
        m.execute(CollectionTasks.getTasks().size());
    }

    @FXML
    protected void updateTableTasks() {
        CollectionTasks.getTasks().add(new Task("", 0, 0, 0, false));
        CollectionTasks.getTasks().remove(CollectionTasks.getTasks().size()-1);

/*
        for (int i = 0; i < collectionTasks.getTasks().size(); i++) {
            //Обновить все поля таблицы
            //execute.getColumns().get(i).setVisible(false);
            //execute.getColumns().get(i).setVisible(true);
            timeActivationTask.getColumns().sorted();
            execute.getColumns().sorted();
            timeActivationTask.getColumns().retainAll();
            execute.getColumns().sorted();
        }
        */
    }
}
