package live.daniel.TaskManager;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {
    private SimpleStringProperty Name;
    private SimpleIntegerProperty Priority;
    private SimpleIntegerProperty TimeActivation;
    private SimpleIntegerProperty TimeUsing;
    private SimpleBooleanProperty Using;
    private boolean ready = false;
    private boolean end = false;

    public Task(String name, int P, int TA, int TU, boolean U) {
        this.Name = new SimpleStringProperty(name);
        this.Priority = new SimpleIntegerProperty(P);
        this.TimeActivation = new SimpleIntegerProperty(TA);
        this.TimeUsing = new SimpleIntegerProperty(TU);
        this.Using = new SimpleBooleanProperty(U);
    }

    public String getName() {
        return Name.get();
    }
    public SimpleStringProperty nameProperty() {
        return Name;
    }
    public void setName(String name) {
        this.Name.set(name);
    }

    public int getPriority() {
        return Priority.get();
    }
    public SimpleIntegerProperty priorityProperty() {
        return Priority;
    }
    public void setPriority(int priority) {
        this.Priority.set(priority);
    }

    public int getTimeActivation() {
        return TimeActivation.get();
    }
    public SimpleIntegerProperty timeActivationProperty() {
        return TimeActivation;
    }
    public void setTimeActivation(int timeActivation) {
        this.TimeActivation.set(timeActivation);
    }

    public int getTimeUsing() {
        return TimeUsing.get();
    }
    public SimpleIntegerProperty timeUsingProperty() {
        return TimeUsing;
    }
    public void setTimeUsing(int timeUsing) {
        this.TimeUsing.set(timeUsing);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isUsing() {
        return Using.get();
    }
    public SimpleBooleanProperty usingProperty() {
        return Using;
    }
    public void setUsing(boolean using) {
        this.Using.set(using);
    }

    public boolean isReady() {
        return ready;
    }
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isEnd() {
        return end;
    }
    public void setEnd(boolean end) {
        this.end = end;
    }

}
