package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CollectionTasks implements ListTasks {
    @SuppressWarnings("WeakerAccess")
    static final ObservableList<Task> tasks = FXCollections.observableArrayList();

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
