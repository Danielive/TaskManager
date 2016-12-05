package live.daniel.TaskManager;

import live.daniel.TaskManager.controllers.mainForm;

import java.util.ArrayList;
import java.util.Date;

public class Processor extends mainForm implements Runnable {
    //static ObservableList<Task> tasks = FXCollections.observableArrayList();

    static ArrayList<Task> tasks = new ArrayList<>();
    /*Processor () {

        List<Tasks> sublist = tasks.subList(4, tasks.size());
        tasks.removeAll(sublist);

    }*/


    //Получает доступ к коллекции и выполняет задачи
    @Override
    public void run() {
        processCommand();
        int time;
        String name;

        //get access to mainForm and get ArrayList for execute
        synchronized (tasks) {
            time = tasks.get(0).getTimeUsing();
            name = tasks.get(0).getName();

            System.out.println(name + " time: " + time + " " + Thread.currentThread().getName() + " Start. Time = " + new Date().getSeconds());
            System.out.println(tasks.get(0).getName() + tasks.get(1).getName() + tasks.get(2).getName() + tasks.get(3).getName());
            System.out.println(tasks);

            tasks.remove(0);

            System.out.println(tasks.get(0).getName() + tasks.get(1).getName() + tasks.get(2).getName() + tasks.get(3).getName());
        }

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " time: " + time + " " + Thread.currentThread().getName() + " End. Time = "+ new Date().getSeconds());
    }

    public void processCommand() {

    }
}

