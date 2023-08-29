package lk.test.myapplication.managers;

import java.util.function.Consumer;

import lk.test.myapplication.models.task.TaskEntity;

public class TasksManager {
    private static TasksManager singleton;
    private DatabaseManager databaseManager;

    public static TasksManager getInstance(){
        if (singleton == null)
            singleton = new TasksManager();
        return singleton;
    }

    private TasksManager(){
        databaseManager = DatabaseManager.getInstance();
    }

    public void getTasks(Consumer<TaskEntity> onTasksLoaded, Consumer<String> onError){
        // TODO: Check if TaskDao has any tasks, if so return them.
        // If tasks are empty, fetch new list from TaskService.
    }
}
