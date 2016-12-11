package live.daniel.TaskManager;

import live.daniel.TaskManager.controllers.mainForm;

public class Processor extends mainForm implements Runnable {

    @Override
    public void run() {
        int timeActivation = 0, timeExecute = 0, priority = 0;
        boolean using = false;
        int currentReady = 0;
        boolean execute = true;
        int currentTask = 0;
        boolean endExecute = false;
        int countReady;

        while (!endExecute) {
            countReady = 0;
            synchronized (CollectionTasks.getTasks()) {
                System.out.println("START");
                //Смотрим все задачи не Юзинг и не Энд в текущую секунду времени готовую работать
                for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                    if (!CollectionTasks.getTasks().get(i).isUsing() && !CollectionTasks.getTasks().get(i).isEnd()) {
                        if (getCountTimeMain() >= CollectionTasks.getTasks().get(i).getTimeActivation()) {
                            CollectionTasks.getTasks().get(i).setReady(true);
                            countReady++;
                        }
                    }
                }

                //Если одна задача готова - находим ее и передаем на выполнение
                if (countReady == 1) {
                    for (int i = 0; i < CollectionTasks.getTasks().size(); i++)
                        if (CollectionTasks.getTasks().get(i).isReady())
                            currentTask = i;
                    CollectionTasks.getTasks().get(currentTask).setUsing(true);
                    executeTask(currentTask);
                }
                else if (countReady != 1) {
                    int temp = 5;
                    for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                        if (CollectionTasks.getTasks().get(i).isReady()) {
                            if (temp > CollectionTasks.getTasks().get(i).getPriority()) {
                                temp = CollectionTasks.getTasks().get(i).getPriority();
                                currentTask = i;
                            }
                        }
                    }
                    CollectionTasks.getTasks().get(currentTask).setUsing(true);
                    executeTask(currentTask);
                }

                System.out.println("CLEAR READY");
                //Снимаем все флаги Готов
                for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                    CollectionTasks.getTasks().get(i).setReady(false);
                }

                System.out.println("CHECK END TASKS");
                //Проверка Все ли задачи обработаны
                int c = 0;
                for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                    if (CollectionTasks.getTasks().get(i).isEnd()) c++;
                    if (c == CollectionTasks.getTasks().size()) endExecute = true;
                }
            }
            if (endExecute)
                for (int i = 0; i < CollectionTasks.getTasks().size(); i++)
                    CollectionTasks.getTasks().get(i).setUsing(false);
        }
    }

    public void executeTask(int currentTask) {
        System.out.println("EXECUTE");
        if (CollectionTasks.getTasks().get(currentTask).isReady()) {
            CollectionTasks.getTasks().get(currentTask).setTimeUsing(CollectionTasks.getTasks().get(currentTask).getTimeUsing() - 1);
            if (CollectionTasks.getTasks().get(currentTask).getTimeUsing() == 0)
                CollectionTasks.getTasks().get(currentTask).setEnd(true);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CollectionTasks.getTasks().get(currentTask).setUsing(false);
        }
    }
}

