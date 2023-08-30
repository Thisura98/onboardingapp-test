package lk.test.myapplication.models.task;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import lk.test.myapplication.utilities.DatabaseTypeConverters;

@Entity
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    @ColumnInfo(name = "due_date")
    @TypeConverters({DatabaseTypeConverters.class})
    public Date dueDate;

    public TaskStatus status;

    public static TaskEntity fromDto(TaskDto dto){
        TaskEntity entity = new TaskEntity();
        entity.id = 0; // room db will autogenerate for us after insert()
        entity.title = dto.title;
        entity.dueDate = DatabaseTypeConverters.fromTimestamp(dto.dueDate);
        entity.status = TaskStatus.PENDING; // set default state
        return entity;
    }

}
