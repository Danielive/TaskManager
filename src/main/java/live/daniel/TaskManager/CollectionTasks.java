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
    public void update(Task task) {

    }

    @Override
    public void del(Task task) {
        tasks.remove(task);
    }

    public void test() {
        tasks.add(new Task("One", 4, 0, 4, false));
        tasks.add(new Task("Two", 3, 2, 2, false));
        tasks.add(new Task("Three", 2, 4, 5, false));
        tasks.add(new Task("Four", 1, 0, 3, false));
    }
}
