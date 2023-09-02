package lk.test.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import lk.test.myapplication.adapter.TaskListAdapter;
import lk.test.myapplication.managers.TasksManager;
import lk.test.myapplication.models.task.TaskEntity;
import lk.test.myapplication.models.task.TaskStatus;

public class MainActivity extends AppCompatActivity {

    private TasksManager tasksManager;
    private Toolbar toolbar;
    private RelativeLayout loadingView;
    private RecyclerView recyclerView;
    private TaskListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        loadingView = findViewById(R.id.loadingView);
        recyclerView = findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);
        setupRecyclerView();

        tasksManager = TasksManager.getInstance();
        loadData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_info){
            showInformationActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setIsLoading(boolean isLoading){
        loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    private void loadData(){
        setIsLoading(true);
        tasksManager.getTasks(
            this::handleDataLoaded,
            this::handleError
        );
    }

    private void handleDataLoaded(List<TaskEntity> tasks){
        setIsLoading(false);
        adapter.updateData(tasks);
    }

    private void handleError(String error){
        setIsLoading(false);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private void setupRecyclerView(){
        adapter = new TaskListAdapter();
        adapter.setItemClickListener((view, position) -> handleItemClick(position));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void handleItemClick(int position){
        TaskEntity task = adapter.getTaskAt(position);
        TaskStatus nextStatus = tasksManager.getNextTaskStatus(task.status);

        Log.d("Click", "You clicked " + task.title);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (nextStatus == null){
            builder.setTitle("Task already completed");
            builder.setMessage("You cannot change the state of completed tasks");
            builder.setPositiveButton("OK", null);
        }
        else{
            String nextStatusName = String.format("\"%s\"?", nextStatus);

            builder.setTitle("Change task status");
            builder.setMessage("Do you want to change the status to " + nextStatusName);
            builder.setNegativeButton("NO", null);
            builder.setPositiveButton("OK", (dialog, which) -> handleChangeItemStatus(task, nextStatus, position));
        }

        builder.show();
    }

    private void handleChangeItemStatus(TaskEntity task, TaskStatus nextStatus, int position){
        tasksManager.updateTaskStatus(
            task,
            nextStatus,
            newTask -> adapter.updateTaskAt(position, newTask),
            this::handleError);
    }

    private void showInformationActivity(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}