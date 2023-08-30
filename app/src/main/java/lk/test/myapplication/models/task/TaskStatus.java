package lk.test.myapplication.models.task;

public enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED;

    public static TaskStatus fromInteger(int status){
        switch(status){
            case 1: return TaskStatus.IN_PROGRESS;
            case 2: return TaskStatus.COMPLETED;
            default: return TaskStatus.PENDING;
        }
    }

    public int toInteger(){
        switch(this){
            case IN_PROGRESS: return 1;
            case COMPLETED: return 2;
            default: return 0;
        }
    }

    @Override
    public String toString(){
        switch(this){
            case IN_PROGRESS: return "In progress";
            case COMPLETED: return "Completed";
            default: return "Pending";
        }
    }
}