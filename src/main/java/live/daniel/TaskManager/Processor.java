package live.daniel.TaskManager;

import live.daniel.TaskManager.controllers.mainForm;

import java.util.Collection;
import java.util.Collections;

public class Processor extends mainForm implements Runnable {
    @Override
    public void run() {
        getCountTimeMain();

        int timeActivation = 0, timeExecute = 0, priority = 0;
        boolean using = false;
        int currentTask = 0;
        boolean execute = true;

        synchronized (CollectionTasks.getTasks()) {

            //За счет флагов делаем переходы
            for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {

                if (!CollectionTasks.getTasks().get(i).getUsing()) {
                    timeActivation = CollectionTasks.getTasks().get(i).getTimeActivation();
                    timeExecute = CollectionTasks.getTasks().get(i).getTimeUsing();
                    priority = CollectionTasks.getTasks().get(i).getPriority();
                    CollectionTasks.getTasks().get(i).setUsing(true);
                    currentTask = i;
                    break;
                }

            }

        }

        executeTask(currentTask);

    }

    public void executeTask(int currentTask) {
        while(CollectionTasks.getTasks().get(currentTask).getTimeUsing() != 0) {
            CollectionTasks.getTasks().get(currentTask).setTimeUsing(CollectionTasks.getTasks().get(currentTask).getTimeUsing()-1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

