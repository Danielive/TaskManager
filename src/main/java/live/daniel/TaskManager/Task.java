package live.daniel.TaskManager;

public class Task {
    String Name;
    int Priority;
    int TimeActivation;
    int TimeUsing;
    boolean Using;

    Task (String name, int P, int TA, int TU, boolean U) {
        Name = name;
        Priority = P;
        TimeActivation = TA;
        TimeUsing = TU;
        Using = U;
    }

    public void setName(String name) {
        Name = name;
    }
    public String getName() {
        return Name;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }
    public int getPriority() {
        return Priority;
    }

    public void setTimeActivation(int time) {
        TimeActivation = time;
    }
    public int getTimeActivation() {
        return TimeActivation;
    }

    public void setTimeUsing(int time) {
        TimeUsing = time;
    }
    public int getTimeUsing() {
        return TimeUsing;
    }


    public void setUsing(boolean using) {
        Using = using;
    }
    public boolean getUsing() {
        return Using;
    }
}
