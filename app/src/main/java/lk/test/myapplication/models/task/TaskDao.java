package lk.test.myapplication.models.task;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM TaskEntity")
    List<TaskEntity> getAll();

    @Insert
    void insertAll(List<TaskEntity> tasks);
    @Update
    void update(TaskEntity task);

    @Query("DELETE FROM TaskEntity")
    void removeAll();
}
