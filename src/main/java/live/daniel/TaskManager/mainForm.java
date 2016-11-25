package live.daniel.TaskManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import live.daniel.TaskManager.Manager.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Daniel on 24.11.2016.
 */
public class mainForm {
    @FXML
    protected Label lbl;
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
    protected TableColumn numTask;
    @FXML
    protected TableColumn NameTask;
    @FXML
    protected TableColumn priorityTask;
    @FXML
    protected TableColumn timeTask;

    Image icon = new Image(getClass().getResourceAsStream("/img/icon.ico"));

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
            if(singleFile.getName().endsWith(".mp3")) {
                System.out.println(singleFile.getName());
            }
        }
        listTasks.setRoot(root);

        /** Инициализация столбца - Установка типа и значения столбца - Добавление данных в столбец
         * private TableColumn<User, Integer> idColumn;
         * idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
         * usersData.add(new User(1, "Alex", "qwerty", "alex@mail.com"));
         */
    }

    @FXML
    protected void clickMouseTreeView(MouseEvent event) {
        TreeItem<String> item = listTasks.getSelectionModel().getSelectedItem();
        System.out.println(item.getValue());
    }

    @FXML
    protected void setAddTask() {
        String txt = tfl.getText();
        lbl.setText(txt);
    }

    @FXML
    protected int setSizeProc() {
        String sizeS = sizeProc.getText();
        int sizeInt = Integer.parseInt(sizeS);
        lbl.setText(sizeS);
        return sizeInt;
    }

    @FXML
    protected String getSizeProc() {
        return sizeProc.getText();
    }

    @FXML
    protected void startProcess() {
        Manager m = new Manager();
        m.execute();
    }
}
