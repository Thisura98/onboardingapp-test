package lk.test.myapplication.managers;

import android.util.Log;

import androidx.room.Room;

import java.util.function.Consumer;

import lk.test.myapplication.models.database.AppDatabase;
import lk.test.myapplication.utilities.MainThreadHelper;

public class DatabaseManager {

    private static DatabaseManager singleton;
    private final String databaseName = "appdb";
    private ContextManager contextManager;
    private AppDatabase database;
    public static DatabaseManager getInstance(){
        if (singleton == null)
            singleton = new DatabaseManager();
        return singleton;
    }

    private DatabaseManager(){
        contextManager = ContextManager.getInstance();
        database = Room.databaseBuilder(
            contextManager.getApplicationContext(),
            AppDatabase.class,
            databaseName).build();
    }

    public AppDatabase db(){
        return database;
    }

    public void clearDatabase(Runnable onSuccess, Consumer<String> onError){
        new Thread(() -> {
            try{
                database.clearAllTables();
                MainThreadHelper.onMainThread(onSuccess);
            }
            catch(Exception e){
                Log.e("ClearDB", e.toString());
                onError.accept("Error occurred while clearing database");
            }

        }).start();
    }

}
