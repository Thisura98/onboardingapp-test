package lk.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

import lk.test.myapplication.managers.DatabaseManager;
import lk.test.myapplication.managers.TasksManager;
import lk.test.myapplication.models.task.TaskDao;
import lk.test.myapplication.models.task.TaskEntity;
import lk.test.myapplication.models.task.TaskService;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RelativeLayout loadingView;
    private RecyclerView recyclerView;

    private TasksManager tasksManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);
        setupRecyclerView();

        tasksManager = TasksManager.getInstance();
        loadData();
    }

    private void setupRecyclerView(){
        // TODO: Setup recycler view
    }

    private void setIsLoading(boolean isLoading){
        loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    private void loadData(){
        setIsLoading(true);
        tasksManager.getTasks(
            tasks -> handleDataLoaded(tasks),
            error -> handleError(error)
        );
    }

    private void handleDataLoaded(List<TaskEntity> tasks){
        setIsLoading(false);
        for (TaskEntity t : tasks){
            Log.d("Entity", String.format("%d %s %s", t.id, t.title, t.status));
        }
    }

    private void handleError(String error){
        setIsLoading(false);
        Log.d("Error", error);
    }
}