package live.daniel.TaskManager;

public class Task {
    private String Name;
    private int Priority;
    private int TimeActivation;
    private int TimeUsing;
    private boolean Using;

    public Task(String name, int P, int TA, int TU, boolean U) {
        setName(name);
        setPriority(P);
        setTimeActivation(TA);
        setTimeUsing(TU);
        setUsing(U);
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public int getPriority() {
        return Priority;
    }
    public void setPriority(int priority) {
        Priority = priority;
    }

    public int getTimeActivation() {
        return TimeActivation;
    }
    public void setTimeActivation(int timeActivation) {
        TimeActivation = timeActivation;
    }

    public int getTimeUsing() {
        return TimeUsing;
    }
    public void setTimeUsing(int timeUsing) {
        TimeUsing = timeUsing;
    }

    public boolean isUsing() {
        return Using;
    }
    public void setUsing(boolean using) {
        Using = using;
    }
}
