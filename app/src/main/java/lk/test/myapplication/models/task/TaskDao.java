package lk.test.myapplication.models.task;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM TaskEntity")
    List<TaskEntity> getAll();

    @Insert
    void insertAll(List<TaskEntity> tasks);

    @Query("DELETE FROM TaskEntity")
    void removeAll();
}
