package live.daniel.TaskManager;

/**
 * Created by Daniel on 24.11.2016.
 */
public class Processor extends Thread {
    public void run() {
            try {
                sleep(100);
            } catch(InterruptedException e) {
                System.err.println("Interrupted");
            }

    }
}
