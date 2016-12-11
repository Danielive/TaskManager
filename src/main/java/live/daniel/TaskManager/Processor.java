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
                System.out.println("START");
                //Смотрим все задачи не Юзинг и не Энд в текущую секунду времени готовую работать
                for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                    if (!CollectionTasks.getTasks().get(i).isUsing() && !CollectionTasks.getTasks().get(i).isEnd()) {
                        if (getCountTimeMain() >= CollectionTasks.getTasks().get(i).getTimeActivation()) {
                            System.out.println("TIME: Time=" + getCountTimeMain() + " >= TimeActivation=" + CollectionTasks.getTasks().get(i).getTimeActivation() + " numb task=" + i);
                            CollectionTasks.getTasks().get(i).setReady(true);
                            countReady++;
                        }
                    }
                }
                System.out.println("Count ready: " + countReady);
                if (countReady == 0) {
                    System.out.println("NULL: Count ready: " + countReady);
                    endExecute = checkEnd();
                    if (endExecute) return;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //Если одна задача готова - находим ее и передаем на выполнение
                else if (countReady == 1) {
                    System.out.println("ONE: Count ready: " + countReady);
                    for (int i = 0; i < CollectionTasks.getTasks().size(); i++)
                        if (CollectionTasks.getTasks().get(i).isReady() && !CollectionTasks.getTasks().get(i).isUsing())
                            currentTask = i;
                    CollectionTasks.getTasks().get(currentTask).setUsing(true);
                } else if (countReady != 1) {
                    System.out.println("TWO: Count ready: " + countReady);
                    int temp = 5;
                    for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                        if (CollectionTasks.getTasks().get(i).isReady() && !CollectionTasks.getTasks().get(i).isUsing()) {
                            System.out.println("isReady task= " + i);
                            if (temp > CollectionTasks.getTasks().get(i).getPriority()) {
                                System.out.println("temp= " + temp + " > Priority= " + CollectionTasks.getTasks().get(i).getPriority() + " Numb task= " + i);
                                temp = CollectionTasks.getTasks().get(i).getPriority();
                                currentTask = i;
                            }
                        }
                    }
                    CollectionTasks.getTasks().get(currentTask).setUsing(true);
                }
            }

            System.out.println("RESULT: Current task: " + currentTask);
            if (countReady != 0) {
                executeTask(currentTask);

                synchronized (CollectionTasks.getTasks()) {
                    System.out.println("CLEAR READY");
                    //Снимаем все флаги Готов
                    for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                        CollectionTasks.getTasks().get(i).setReady(false);
                    }
                }

                System.out.println("CHECK END TASKS");
                //Проверка Все ли задачи обработаны
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
        System.out.println("EXECUTE");
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

