package live.daniel.TaskManager;

import live.daniel.TaskManager.controllers.mainForm;

public class Processor extends mainForm implements Runnable {

    boolean endExecute = false;

    @Override
    public void run() {
        int currentTask = 0;
        int countReady;

        while (!endExecute) {
            countReady = 0;
            synchronized (CollectionTasks.getTasks()) {
                //Look all the tasks that are not used and not end of the current second time and ready to work
                for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                    if (!CollectionTasks.getTasks().get(i).isUsing() && !CollectionTasks.getTasks().get(i).isEnd()) {
                        if (getCountTimeMain() >= CollectionTasks.getTasks().get(i).getTimeActivation()) {
                            CollectionTasks.getTasks().get(i).setReady(true);
                            countReady++;
                        }
                    }
                }
                if (countReady == 0) {
                    endExecute = checkEnd();
                    if (endExecute) return;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //If one task is ready - find it, and pass on to execute
                else if (countReady == 1) {
                    for (int i = 0; i < CollectionTasks.getTasks().size(); i++)
                        if (CollectionTasks.getTasks().get(i).isReady() && !CollectionTasks.getTasks().get(i).isUsing())
                            currentTask = i;
                    CollectionTasks.getTasks().get(currentTask).setUsing(true);
                } else if (countReady != 1) {
                    int temp = 5;
                    for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                        if (CollectionTasks.getTasks().get(i).isReady() && !CollectionTasks.getTasks().get(i).isUsing()) {
                            if (temp > CollectionTasks.getTasks().get(i).getPriority()) {
                                temp = CollectionTasks.getTasks().get(i).getPriority();
                                currentTask = i;
                            }
                        }
                    }
                    CollectionTasks.getTasks().get(currentTask).setUsing(true);
                }
            }

            if (countReady != 0) {
                executeTask(currentTask);

                synchronized (CollectionTasks.getTasks()) {
                    //Remove all flags ready
                    for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                        CollectionTasks.getTasks().get(i).setReady(false);
                    }
                }

                //Check all tasks on end execute
                endExecute = checkEnd();
                if (endExecute)
                    for (int i = 0; i < CollectionTasks.getTasks().size(); i++)
                        CollectionTasks.getTasks().get(i).setUsing(false);
            }
        }
    }

    private boolean checkEnd() {
        int c = 0;
        for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
            if (CollectionTasks.getTasks().get(i).isEnd()) c++;
            if (c == CollectionTasks.getTasks().size()) endExecute = true;
        }
        return endExecute;
    }

    public void executeTask(int currentTask) {
        if (CollectionTasks.getTasks().get(currentTask).isReady()) {
            CollectionTasks.getTasks().get(currentTask).setTimeUsing(CollectionTasks.getTasks().get(currentTask).getTimeUsing() - 1);
            if (CollectionTasks.getTasks().get(currentTask).getTimeUsing() == 0) {
                CollectionTasks.getTasks().get(currentTask).setEnd(true);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CollectionTasks.getTasks().get(currentTask).setUsing(false);
        }
    }
}

