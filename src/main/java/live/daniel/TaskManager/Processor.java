package live.daniel.TaskManager;

import live.daniel.TaskManager.controllers.mainForm;

import java.util.ArrayList;
import java.util.Date;

public class Processor extends mainForm implements Runnable {

    @Override
    public void run() {
        processCommand();
        int timeActivation = 0, timeExecute = 0, priority;
        boolean using;
        int currentTask = 0;

        //get access to mainForm and get ArrayList for execute
        synchronized (CollectionTasks.getTasks()) {
            for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                if (!CollectionTasks.getTasks().get(i).isUsing()) {
                    timeActivation = CollectionTasks.getTasks().get(i).getTimeActivation();
                    timeExecute = CollectionTasks.getTasks().get(i).getTimeUsing();
                    priority = CollectionTasks.getTasks().get(i).getPriority();
                    CollectionTasks.getTasks().get(i).setUsing(true);
                    currentTask = i;
                    break;
                }
            }
            System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date().getSeconds());
            System.out.println(
                    CollectionTasks.getTasks().get(currentTask).getName() + " " +
                    CollectionTasks.getTasks().get(currentTask).getPriority() + " " +
                    CollectionTasks.getTasks().get(currentTask).getTimeActivation() + " " +
                    CollectionTasks.getTasks().get(currentTask).getTimeUsing() + " " +
                    CollectionTasks.getTasks().get(currentTask).isUsing()
            );
            try {
                Thread.sleep(timeExecute * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void processCommand() {

    }
}

