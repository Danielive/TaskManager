package live.daniel.TaskManager;

import javafx.fxml.FXML;

/**
 * Created by Daniel on 24.11.2016.
 */
public class Manager extends mainForm{

    private Processor[] processors = new Processor[setSizeProc()];

    public void execute() {
       for (int i = 0; i < processors.length; i++)
           processors[i].start();
    }
}
