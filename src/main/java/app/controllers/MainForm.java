package app.controllers;

import app.CollectionTasks;
import app.Manager;
import app.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("unused")
public class MainForm {
    private static final CollectionTasks collectionTasks = new CollectionTasks();

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

    public MainForm() {

    }

    @SuppressWarnings("WeakerAccess")
    static int countP = 1;

    @SuppressWarnings("WeakerAccess")
    static volatile boolean running = false;
    protected void setRunning(@SuppressWarnings("SameParameterValue") boolean running) {
        //noinspection AccessStaticViaInstance
        this.running = running;
    }

    @SuppressWarnings("WeakerAccess")
    static volatile int countTimeMain = 0;
    protected static int getCountTimeMain() {
        return countTimeMain;
    }

    @SuppressWarnings("WeakerAccess")
    static volatile boolean txt;
    @SuppressWarnings("WeakerAccess")
    static volatile boolean jpeg;
    @SuppressWarnings("WeakerAccess")
    static volatile boolean mp3;
    @SuppressWarnings("WeakerAccess")
    static volatile boolean exe;

    @SuppressWarnings("WeakerAccess")
    static volatile boolean endExecute;
    protected boolean isEndExecute() {
        return endExecute;
    }
    protected void setEndExecute(boolean endExecute) {
        //noinspection AccessStaticViaInstance
        this.endExecute = endExecute;
    }

    protected boolean isMp3() {
        return mp3;
    }
    protected void setMp3(boolean mp3) {
        //noinspection AccessStaticViaInstance
        this.mp3 = mp3;
    }
    protected boolean isJpeg() {
        return jpeg;
    }
    protected void setJpeg(boolean jpeg) {
        //noinspection AccessStaticViaInstance
        this.jpeg = jpeg;
    }
    protected boolean isTxt() {
        return txt;
    }
    protected void setTxt(boolean txt) {
        //noinspection AccessStaticViaInstance
        this.txt = txt;
    }
    protected boolean isExe() {
        return exe;
    }
    protected void setExe(boolean exe) {
        //noinspection AccessStaticViaInstance
        this.exe = exe;
    }

    @SuppressWarnings("WeakerAccess")
    TreeItem<String> item;
    @SuppressWarnings("WeakerAccess")
    final Image icon = new Image(getClass().getResourceAsStream("/img/tasks.png"));

    //It is checked method getName(), PropertyValueFactory - "Name" or nameProperty(), PropertyValueFactory - "name"
    @SuppressWarnings("unchecked")
    @FXML
    protected void initialize() {
        nameTask.setCellValueFactory(new PropertyValueFactory<>("name"));
        priorityTask.setCellValueFactory(new PropertyValueFactory<>("priority"));
        timeActivationTask.setCellValueFactory(new PropertyValueFactory<>("timeActivation"));
        timeExecuteTask.setCellValueFactory(new PropertyValueFactory<>("timeUsing"));
        execute.setCellValueFactory(new PropertyValueFactory<>("using"));
        tableTasks.setItems(CollectionTasks.getTasks());
        setTreeView();

        //tested();

        //noinspection AccessStaticViaInstance
        collectionTasks.getTasks().addListener((ListChangeListener<Task>) c -> updateCountTasks());
    }

    @SuppressWarnings("WeakerAccess")
    protected void updateCountTasks() {
        countTasks.setText("Count tasks: " + CollectionTasks.getTasks().size());
    }
    @SuppressWarnings("WeakerAccess")
    protected void updateCountProcessor() {
        countProcessor.setText("Count processor: " + countP);
    }
    @SuppressWarnings("WeakerAccess")
    protected void updateTimeMain() {
        timeMain.setText("Time: " + countTimeMain);
    }

    protected int getTimeActivation() {
        //noinspection AccessStaticViaInstance
        return collectionTasks.getTasks().get(0).getTimeActivation();
    }
    protected int getCountP() {
        return countP;
    }

    @SuppressWarnings("WeakerAccess")
    @FXML
    protected void setTreeView() {
        TreeItem<String> root = new TreeItem<>("Tasks", new ImageView(icon));
        root.setExpanded(true);

        File rootTasks = new File("D:\\Projects\\IntelliJIDEA\\task-manager\\src\\main\\resources\\tasks");
        ArrayList<File> al = new ArrayList<>();
        File[] files = rootTasks.listFiles();

        assert files != null;
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
        //noinspection StringEquality
        if (item.getValue() == "Tasks") //noinspection UnnecessaryReturnStatement
            return;
        else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainForm.class.getResource("/fxml/addTask.fxml"));
                Parent content = loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Add task");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.getIcons().add(new Image("/img/main.png"));
                Scene scene = new Scene(content);
                dialogStage.setScene(scene);
                AddTask controller = loader.getController();
                controller.setDialogStage(dialogStage);
                dialogStage.setResizable(false);
                dialogStage.showAndWait();

                if (controller.isOkClicked()) {
                    if (item.getValue().endsWith(".exe"))
                        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size() + 1 + "_" + item.getValue(), controller.getPriorityTask(), controller.getTimeActivation(), controller.getTimeExecute(), false, 1));
                    else if (item.getValue().endsWith(".mp3"))
                        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size() + 1 + "_" + item.getValue(), controller.getPriorityTask(), controller.getTimeActivation(), controller.getTimeExecute(), false, 2));
                    else if (item.getValue().endsWith(".jpeg"))
                        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size() + 1 + "_" + item.getValue(), controller.getPriorityTask(), controller.getTimeActivation(), controller.getTimeExecute(), false, 3));
                    else if (item.getValue().endsWith(".txt"))
                        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size() + 1 + "_" + item.getValue(), controller.getPriorityTask(), controller.getTimeActivation(), controller.getTimeExecute(), false, 4));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void setDelTask() {
        if (!running) {
            String temp;
            Task selectedTask = (Task) tableTasks.getSelectionModel().getSelectedItem();
            //noinspection AccessStaticViaInstance
            collectionTasks.getTasks().remove(selectedTask);
            for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                //noinspection AccessStaticViaInstance
                temp = collectionTasks.getTasks().get(i).getName().replaceAll("\\d_", "");
                //noinspection AccessStaticViaInstance
                collectionTasks.getTasks().get(i).setName(temp);
                //noinspection AccessStaticViaInstance,AccessStaticViaInstance
                collectionTasks.getTasks().get(i).setName(i + 1 + "_" + collectionTasks.getTasks().get(i).getName());
            }
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
            loader.setLocation(MainForm.class.getResource("/fxml/setProcessor.fxml"));
            Parent content = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Count processor");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.getIcons().add(new Image("/img/main.png"));
            Scene scene = new Scene(content);
            dialogStage.setScene(scene);
            SetProcessor controller = loader.getController();
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

    @SuppressWarnings("RedundantThrows")
    @FXML
    protected void startProgram() throws InterruptedException {
        //noinspection AccessStaticViaInstance
        if (!collectionTasks.getTasks().isEmpty() && !running) {
            setTxt(false);
            setJpeg(false);
            setMp3(false);
            setExe(false);
            setEndExecute(false);
            runClock();
            //sortingTasks();
            executeTasks();
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected void runClock() {
        running = true;
        countTimeMain = 0;
        new Thread() {
            public void run() {
                long last = System.nanoTime();
                double delta = 0;
                double ns = 1000000000;
                while (running) {
                    long now = System.nanoTime();
                    delta += (now - last) / ns;
                    last = now;
                    while (delta >= 1) {
                        countTimeMain = (countTimeMain + 1) % 999;
                        delta--;
                        Platform.runLater(() -> updateTimeMain());
                    }
                }
            }
        }.start();
    }

    @SuppressWarnings("WeakerAccess")
    protected void executeTasks() {
        Manager m = new Manager();
        new Thread() {
            public void run() {
                try {
                    m.execute(CollectionTasks.getTasks().size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    protected void sortingTasks() {
        int countD = 1; int tCountD = 1;
        //Sorting to TimeActivation
        //noinspection AccessStaticViaInstance
        FXCollections.sort(collectionTasks.getTasks(), (o1, o2) ->
                Integer.compare(o1.getTimeActivation(), o2.getTimeActivation()));
        //Sorting to Priority and find all equals TimeActivation for total sorting priorities
        //noinspection AccessStaticViaInstance
        for (int i = 0; i < collectionTasks.getTasks().size(); i++) {
            //noinspection AccessStaticViaInstance
            if (collectionTasks.getTasks().size() != i + 1) {
                //noinspection AccessStaticViaInstance,AccessStaticViaInstance
                if (collectionTasks.getTasks().get(i).getTimeActivation() == collectionTasks.getTasks().get(i + 1).getTimeActivation()) {
                    tCountD = tCountD + 1;
                    //noinspection AccessStaticViaInstance,AccessStaticViaInstance
                    if (collectionTasks.getTasks().get(i).getPriority() > collectionTasks.getTasks().get(i + 1).getPriority())
                        //noinspection AccessStaticViaInstance
                        Collections.swap(collectionTasks.getTasks(), i, i + 1);
                } else tCountD = 1;
            }
            if (tCountD > countD) countD = tCountD;
        }
        //Total sorting priorities
        for (int d = 0; d < countD; d++) {
            //noinspection AccessStaticViaInstance
            for (int i = 0; i < collectionTasks.getTasks().size(); i++) {
                //noinspection AccessStaticViaInstance
                if (collectionTasks.getTasks().size() != i + 1)
                    //noinspection AccessStaticViaInstance,AccessStaticViaInstance
                    if (collectionTasks.getTasks().get(i).getTimeActivation() == collectionTasks.getTasks().get(i + 1).getTimeActivation()) {
                        //noinspection AccessStaticViaInstance,AccessStaticViaInstance
                        if (collectionTasks.getTasks().get(i).getPriority() > collectionTasks.getTasks().get(i + 1).getPriority())
                            //noinspection AccessStaticViaInstance
                            Collections.swap(collectionTasks.getTasks(), i, i + 1);
                    }
            }
        }
    }

    private void tested() {
        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size()+ 1 + "_TEST.exe", 1, 1, 3, false, 1));
        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size()+ 1 + "_TEST.txt", 4, 1, 3, false, 4));
        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size()+ 1 + "_TEST.jpeg", 3, 1, 3, false, 3));
        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size()+ 1 + "_TEST.jpeg", 3, 1, 3, false, 3));
        CollectionTasks.getTasks().add(new Task(CollectionTasks.getTasks().size()+ 1 + "_TEST.mp3", 2, 1, 3, false, 2));
    }
}
