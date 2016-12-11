package live.daniel.TaskManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Daniel on 04.12.2016.
 */
public class CollectionTasks implements ListTasks {
    static ObservableList<Task> tasks = FXCollections.observableArrayList();

    public static ObservableList<Task> getTasks() {
        return tasks;
    }

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public void del(Task task) {
        tasks.remove(task);
    }
}
