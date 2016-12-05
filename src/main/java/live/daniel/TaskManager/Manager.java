package live.daniel.TaskManager;

import live.daniel.TaskManager.controllers.mainForm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Manager extends mainForm {
    ExecutorService ThreadPool = Executors.newFixedThreadPool(getCountP());

    public void execute(int tasks) throws InterruptedException {
        for (int i = 0; i < tasks; i++) {
            Processor worker = new Processor();
            ThreadPool.execute(worker);
        }

        Thread.sleep(1000);
        ThreadPool.shutdown();
        while(!ThreadPool.isTerminated()){
            //wait for all tasks to finish
        }
        System.out.println("Finished");
    }
}
