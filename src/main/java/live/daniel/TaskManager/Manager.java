package live.daniel.TaskManager;

import live.daniel.TaskManager.controllers.mainForm;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class Manager extends mainForm {
    @SuppressWarnings("WeakerAccess")
    final ScheduledExecutorService ThreadPool = Executors.newScheduledThreadPool(getCountP());

    public void execute(int tasks) throws InterruptedException {
        for (int i = 0; i < tasks; i++) {
            Processor worker = new Processor();
            ThreadPool.schedule(worker, getTimeActivation(), TimeUnit.SECONDS);
        }
        Thread.sleep(1000);
        ThreadPool.shutdown();
        //noinspection StatementWithEmptyBody
        while(!ThreadPool.isTerminated()){
            //wait for all tasks to finish
        }
        System.out.println("Finished");
        setRunning(false);
    }
}
