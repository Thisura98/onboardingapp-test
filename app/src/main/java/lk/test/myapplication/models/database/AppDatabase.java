package lk.test.myapplication.models.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import lk.test.myapplication.models.task.TaskEntity;
import lk.test.myapplication.models.task.TaskDao;
import lk.test.myapplication.utilities.DatabaseTypeConverters;

@Database(entities = {TaskEntity.class}, version = 1)
@TypeConverters({DatabaseTypeConverters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
