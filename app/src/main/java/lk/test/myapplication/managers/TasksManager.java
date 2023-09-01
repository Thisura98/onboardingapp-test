package lk.test.myapplication.managers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import lk.test.myapplication.models.task.TaskDao;
import lk.test.myapplication.retrofit.task.TaskDto;
import lk.test.myapplication.models.task.TaskEntity;
import lk.test.myapplication.retrofit.task.TaskResponse;
import lk.test.myapplication.retrofit.task.TaskService;
import lk.test.myapplication.models.task.TaskStatus;
import lk.test.myapplication.utilities.MainThreadHelper;
import retrofit2.Response;

public class TasksManager {
    private static TasksManager singleton;
    private DatabaseManager databaseManager;
    private TaskService taskService;

    public static TasksManager getInstance(){
        if (singleton == null)
            singleton = new TasksManager();
        return singleton;
    }

    private TasksManager(){
        databaseManager = DatabaseManager.getInstance();
        taskService = NetworkManager.getInstance().createService(TaskService.class);
    }

    /**
     * Check if the local db has any tasks, if so return them. Otherwise,
     * fetch new list from the server.
     */
    public void getTasks(Consumer<List<TaskEntity>> onTasksLoaded, Consumer<String> onError){

        /**
         * Always perform db operations in the background thread.
         * In Java, this is done by creating a new Thread and passing a "Runnable" object.
         */

        new Thread(() -> {
            TaskDao dao = databaseManager.db().taskDao();
            List<TaskEntity> dbTasks = dao.getAll();

            if (dbTasks == null || dbTasks.size() > 0){
                MainThreadHelper.onMainThread(() -> onTasksLoaded.accept(dbTasks));
            }
            else{
                if (!NetworkManager.getInstance().isNetworkAvailable()){
                    MainThreadHelper.onMainThread(() -> onError.accept("No internet connection"));
                }

                List<TaskDto> serverTasks = getTasksFromServer();
                if (serverTasks == null){
                    MainThreadHelper.onMainThread(() -> onError.accept("Could not fetch tasks from server"));
                }
                else{
                    saveServerTasks(serverTasks);
                    List<TaskEntity> newDbTasks = dao.getAll();
                    MainThreadHelper.onMainThread(() -> onTasksLoaded.accept(newDbTasks));
                }
            }
        }).start();
    }

    public TaskStatus getNextTaskStatus(TaskStatus status){
        switch(status){
            case PENDING: return TaskStatus.IN_PROGRESS;
            case IN_PROGRESS: return TaskStatus.COMPLETED;
            default: return null;
        }
    }

    public void updateTaskStatus(TaskEntity task, TaskStatus nextStatus, Consumer<TaskEntity> onSuccess, Consumer<String> onError){
        new Thread(new Runnable() {
            @Override
            public void run() {
                task.status = nextStatus;

                try{
                    databaseManager.db().taskDao().update(task);
                    MainThreadHelper.onMainThread(() -> onSuccess.accept(task));
                }
                catch(Exception e){
                    Log.e("Update", e.toString());
                    MainThreadHelper.onMainThread(() -> {
                        onError.accept("Error occurred while trying to change task status");
                    });
                }
            }
        }).start();
    }

    /**
     * Synchronously retrieves the tasks from the server.
     * Always call this method from a background thread.
     */
    private List<TaskDto> getTasksFromServer() {
        try{
            Response<TaskResponse> response = taskService.tasks().execute();
            List<TaskDto> tasks = response.body().tasks;
            return tasks;
        }
        catch(Exception e){
            Log.d("Error", e.toString());
            return null;
        }
    }

    private void saveServerTasks(List<TaskDto> serverTasks){
        // Remove existing data to prevent conflicts.
        // Alternatively, we can introduce a conflict resolution strategy.
        databaseManager.db().taskDao().removeAll();

        List<TaskEntity> tempEntities = new ArrayList<>();
        for(TaskDto t : serverTasks){
            tempEntities.add(TaskEntity.fromDto(t));
        }

        databaseManager.db().taskDao().insertAll(tempEntities);
    }
}
