package lk.test.myapplication.models.task;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import lk.test.myapplication.utilities.TimestampConverter;

@Entity
public class TaskEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;

    @ColumnInfo(name = "due_date")
    @TypeConverters({TimestampConverter.class})
    public Date dueDate;

    public int status;

}
