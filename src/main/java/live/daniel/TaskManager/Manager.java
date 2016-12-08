package live.daniel.TaskManager;

import live.daniel.TaskManager.controllers.mainForm;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Manager extends mainForm {
    ScheduledExecutorService ThreadPool = Executors.newScheduledThreadPool(getCountP());

    /***
     * @param tasks
     * @throws InterruptedException
     */
    public void execute(int tasks) throws InterruptedException {
        for (int i = 0; i < tasks; i++) {
            Processor worker = new Processor();
            ThreadPool.schedule(worker, getTimeActivation(), TimeUnit.SECONDS);
        }
        Thread.sleep(1000);
        ThreadPool.shutdown();
        while(!ThreadPool.isTerminated()){
            //wait for all tasks to finish
        }
        System.out.println("Finished");
        setRunning(false);
    }
}
