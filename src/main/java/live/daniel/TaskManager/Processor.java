package live.daniel.TaskManager;

import live.daniel.TaskManager.controllers.mainForm;

@SuppressWarnings("unused")
class Processor extends mainForm implements Runnable {

    @Override
    public void run() {
        int currentTask = 0;
        int countReady;

        while (!isEndExecute()) {
            countReady = 0;
            System.out.println("BEGIN#TIME: " + getCountTimeMain());
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
                    setEndExecute(checkEnd());
                    if (isEndExecute()) return;
                }
                //If one task is ready - find it, and pass on to execute
                else if (countReady == 1) {
                    for (int i = 0; i < CollectionTasks.getTasks().size(); i++)
                        if (CollectionTasks.getTasks().get(i).isReady() && !CollectionTasks.getTasks().get(i).isEnd() && !CollectionTasks.getTasks().get(i).isUsing())
                            currentTask = i;
                    System.out.println("1#countR=1");
                } else {
                    int temp = 5; int minTime = 1000;
                    for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                        if (CollectionTasks.getTasks().get(i).isReady() && !CollectionTasks.getTasks().get(i).isEnd() && !CollectionTasks.getTasks().get(i).isUsing()) {
                            if (temp > CollectionTasks.getTasks().get(i).getPriority()) {
                                temp = CollectionTasks.getTasks().get(i).getPriority();
                                //currentTask = i;
                                ///find minimal time task
                            }
                        }
                    }
                    for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                        if (CollectionTasks.getTasks().get(i).isReady() && !CollectionTasks.getTasks().get(i).isEnd() && !CollectionTasks.getTasks().get(i).isUsing() && temp == CollectionTasks.getTasks().get(i).getPriority()) {
                            if (minTime > CollectionTasks.getTasks().get(i).getTimeUsing()) {
                                minTime = CollectionTasks.getTasks().get(i).getTimeUsing();
                                currentTask = i;
                            }
                        }
                    }
                    System.out.println("2#countR>1");
                }
                determineTypeTask(currentTask);
            }
            choiceResult(currentTask);
        }
    }

    private void choiceResult(int currentTask) {
        if (CollectionTasks.getTasks().get(currentTask).isAccess() && !CollectionTasks.getTasks().get(currentTask).isUsing() && CollectionTasks.getTasks().get(currentTask).isReady()) {
            System.out.println("choice#ACCESS");
            handleEnd(currentTask);
        }
        else if (!CollectionTasks.getTasks().get(currentTask).isEnd() && !CollectionTasks.getTasks().get(currentTask).isUsing() && CollectionTasks.getTasks().get(currentTask).isReady()) {
            System.out.println("choice#SLEEP or NEW TASK");
            findNotUsingIfNotAccess(CollectionTasks.getTasks().get(currentTask).getPriority());
        }
    }

    @SuppressWarnings("FieldCanBeLocal")
    private int t; private int minT = 1000;
    private void findNotUsingIfNotAccess(int numbFile) {
        t = 5;
        int numTask = -1;
        synchronized (CollectionTasks.getTasks()) {
            for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                if (CollectionTasks.getTasks().get(i).isReady() && !CollectionTasks.getTasks().get(i).isEnd() && !CollectionTasks.getTasks().get(i).isUsing() && numbFile != CollectionTasks.getTasks().get(i).getPriority()) {
                    if (t > CollectionTasks.getTasks().get(i).getPriority()) {
                        t = CollectionTasks.getTasks().get(i).getPriority();
                        //numTask = i;
                        //find minimal time task
                    }
                }
            }
            for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                if (CollectionTasks.getTasks().get(i).isReady() && !CollectionTasks.getTasks().get(i).isEnd() && !CollectionTasks.getTasks().get(i).isUsing() && t == CollectionTasks.getTasks().get(i).getPriority()) {
                    if (minT > CollectionTasks.getTasks().get(i).getTimeUsing()) {
                        minT = CollectionTasks.getTasks().get(i).getTimeUsing();
                        numTask = i;
                    }
                }
            }
        }

        determineTypeTask(numTask);

        System.out.println("FIND ELEMENT: " + CollectionTasks.getTasks().get(numTask).getName());

        if (numTask != -1) {
            System.out.println("RETURN FIND");
            System.out.println("BEGIN#nameTask: " + CollectionTasks.getTasks().get(numTask).getName() +
                    " executeTask#Access: " + CollectionTasks.getTasks().get(numTask).isAccess() +
                    " executeTask#Using: " + CollectionTasks.getTasks().get(numTask).isUsing() +
                    " executeTask#Ready: " + CollectionTasks.getTasks().get(numTask).isReady() +
                    " executeTask#End: " + CollectionTasks.getTasks().get(numTask).isEnd());
            if (CollectionTasks.getTasks().get(numTask).isAccess() && !CollectionTasks.getTasks().get(numTask).isUsing() && CollectionTasks.getTasks().get(numTask).isReady() && !CollectionTasks.getTasks().get(numTask).isEnd())
                handleEnd(numTask);
        }
        else { System.out.println("NOT FIND ELEMENT"); sleep(); }
    }

    private void determineTypeTask(int numTask) {
        if (CollectionTasks.getTasks().get(numTask).getName().endsWith(".exe")) {
            if (!isExe()) {
                System.out.println("EXE FALSE!");
                if (!CollectionTasks.getTasks().get(numTask).isEnd() && !CollectionTasks.getTasks().get(numTask).isUsing() && CollectionTasks.getTasks().get(numTask).isReady()) {
                    setExe(true);
                    CollectionTasks.getTasks().get(numTask).setAccess(true);
                }
            }
        } else if (CollectionTasks.getTasks().get(numTask).getName().endsWith(".mp3")) {
            if (!isMp3()) {
                System.out.println("MP3 FALSE!");
                if (!CollectionTasks.getTasks().get(numTask).isEnd() && !CollectionTasks.getTasks().get(numTask).isUsing() && CollectionTasks.getTasks().get(numTask).isReady()) {
                    setMp3(true);
                    CollectionTasks.getTasks().get(numTask).setAccess(true);
                }
            }
        } else if (CollectionTasks.getTasks().get(numTask).getName().endsWith(".jpeg")) {
            if (!isJpeg()) {
                System.out.println("JPEG FALSE!");
                if (!CollectionTasks.getTasks().get(numTask).isEnd() && !CollectionTasks.getTasks().get(numTask).isUsing() && CollectionTasks.getTasks().get(numTask).isReady()) {
                    setJpeg(true);
                    CollectionTasks.getTasks().get(numTask).setAccess(true);
                }
            }
        } else if (CollectionTasks.getTasks().get(numTask).getName().endsWith(".txt")) {
            if (!isTxt()) {
                System.out.println("TXT FALSE!");
                if (!CollectionTasks.getTasks().get(numTask).isEnd() && !CollectionTasks.getTasks().get(numTask).isUsing() && CollectionTasks.getTasks().get(numTask).isReady()) {
                    setTxt(true);
                    CollectionTasks.getTasks().get(numTask).setAccess(true);
                }
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleEnd(int currentTask) {
        synchronized (CollectionTasks.getTasks()) {
            if (!CollectionTasks.getTasks().get(currentTask).isUsing()) {
                CollectionTasks.getTasks().get(currentTask).setUsing(true);
                System.out.println("handleEnd#Name task: " + CollectionTasks.getTasks().get(currentTask).getName() + " execute");
            }
            else return;
        }
        executeTask(currentTask);

        synchronized (CollectionTasks.getTasks()) {
            //Remove all flags ready
            for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
                CollectionTasks.getTasks().get(i).setReady(false);
            }
        }
        //Check all tasks on end execute
        setEndExecute(checkEnd());
    }

    private void executeTask(int currentTask) {
        if (CollectionTasks.getTasks().get(currentTask).isUsing()) {
            System.out.println("executeTask#nameTask: " + CollectionTasks.getTasks().get(currentTask).getName() +
                    " executeTask#Access: " + CollectionTasks.getTasks().get(currentTask).isAccess() +
                    " executeTask#Using: " + CollectionTasks.getTasks().get(currentTask).isUsing() +
                    " executeTask#Ready: " + CollectionTasks.getTasks().get(currentTask).isReady() +
                    " executeTask#End: " + CollectionTasks.getTasks().get(currentTask).isEnd());
            System.out.println("executeTask#TimeAct: " + CollectionTasks.getTasks().get(currentTask).getTimeActivation() +
                    " executeTask#TimeExecute: " + CollectionTasks.getTasks().get(currentTask).getTimeUsing());
            System.out.println("TIME: " + getCountTimeMain());

            sleep();
            CollectionTasks.getTasks().get(currentTask).setTimeUsing(CollectionTasks.getTasks().get(currentTask).getTimeUsing() - 1);
            if (CollectionTasks.getTasks().get(currentTask).getTimeUsing() == 0) {
                CollectionTasks.getTasks().get(currentTask).setEnd(true);
                CollectionTasks.getTasks().get(currentTask).setAccess(false);
                System.out.println("executeTask#END");
                if (CollectionTasks.getTasks().get(currentTask).getName().endsWith(".exe")) {
                    setExe(false);
                } else if (CollectionTasks.getTasks().get(currentTask).getName().endsWith(".mp3")) {
                    setMp3(false);
                } else if (CollectionTasks.getTasks().get(currentTask).getName().endsWith(".jpeg")) {
                    setJpeg(false);
                } else if (CollectionTasks.getTasks().get(currentTask).getName().endsWith(".txt")) {
                    setTxt(false);
                }
            }
            CollectionTasks.getTasks().get(currentTask).setUsing(false);
        }
    }

    private boolean checkEnd() {
        int c = 0;
        for (int i = 0; i < CollectionTasks.getTasks().size(); i++) {
            if (CollectionTasks.getTasks().get(i).isEnd()) c++;
            if (c == CollectionTasks.getTasks().size()) {setEndExecute(true);}
        }
        return isEndExecute();
    }
}