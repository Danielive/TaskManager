package app;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {
    private final SimpleStringProperty Name;
    private final SimpleIntegerProperty Priority;
    private final SimpleIntegerProperty TimeActivation;
    private final SimpleIntegerProperty TimeUsing;
    private final SimpleBooleanProperty Using;
    private boolean ready = false;
    private boolean end = false;
    private int numbResource;

    public int getNumbResource() {
        return numbResource;
    }
    @SuppressWarnings("unused")
    public void setNumbResource(int numbResource) {
        this.numbResource = numbResource;
    }

    public boolean isAccess() {
        return access;
    }
    public void setAccess(boolean access) {
        this.access = access;
    }

    private boolean access = false;

    public Task(String name, int P, int TA, int TU, @SuppressWarnings("SameParameterValue") boolean U, int NR) {
        this.Name = new SimpleStringProperty(name);
        this.Priority = new SimpleIntegerProperty(P);
        this.TimeActivation = new SimpleIntegerProperty(TA);
        this.TimeUsing = new SimpleIntegerProperty(TU);
        this.Using = new SimpleBooleanProperty(U);
        this.numbResource = NR;
    }

    public String getName() {
        return Name.get();
    }
    @SuppressWarnings("unused")
    public SimpleStringProperty nameProperty() {
        return Name;
    }
    public void setName(String name) {
        this.Name.set(name);
    }

    public int getPriority() {
        return Priority.get();
    }
    @SuppressWarnings("unused")
    public SimpleIntegerProperty priorityProperty() {
        return Priority;
    }
    @SuppressWarnings("unused")
    public void setPriority(int priority) {
        this.Priority.set(priority);
    }

    public int getTimeActivation() {
        return TimeActivation.get();
    }
    @SuppressWarnings("unused")
    public SimpleIntegerProperty timeActivationProperty() {
        return TimeActivation;
    }
    @SuppressWarnings("unused")
    public void setTimeActivation(int timeActivation) {
        this.TimeActivation.set(timeActivation);
    }

    public int getTimeUsing() {
        return TimeUsing.get();
    }
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
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
    public void setEnd(@SuppressWarnings("SameParameterValue") boolean end) {
        this.end = end;
    }

}
