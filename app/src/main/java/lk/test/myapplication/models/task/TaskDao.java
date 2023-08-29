package lk.test.myapplication.models.task;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM TaskEntity")
    List<TaskEntity> getAll();
}
