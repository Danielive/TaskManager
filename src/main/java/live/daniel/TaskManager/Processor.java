package live.daniel.TaskManager;

import live.daniel.TaskManager.controllers.mainForm;

public class Processor extends mainForm implements Runnable {

    @Override
    public void run() {
        processCommand();
        int timeActivation = 0, timeExecute = 0, priority;
        boolean using;
        int currentTask = 0;
        boolean execute = true;
        synchronized (CollectionTasks.getTasks()) {
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
    }

    public void processCommand() {
        // TODO: 09.12.2016 Основываемся на привязке к таймеру.
        // TODO: 09.12.2016 Реализуем обработку задач сначала последовательно хавая счетчик каждой задачи
        // TODO: 09.12.2016 Реализуем обработку задач по алгоритму
    }
}

