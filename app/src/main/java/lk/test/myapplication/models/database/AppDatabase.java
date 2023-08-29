package lk.test.myapplication.models.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import lk.test.myapplication.models.task.TaskEntity;
import lk.test.myapplication.models.task.TaskDao;

@Database(entities = {TaskEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
