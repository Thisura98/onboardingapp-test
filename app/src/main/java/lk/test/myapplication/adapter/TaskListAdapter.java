package lk.test.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lk.test.myapplication.R;
import lk.test.myapplication.models.task.TaskEntity;
import lk.test.myapplication.viewholder.TaskItemViewHolder;

public class TaskListAdapter extends RecyclerView.Adapter {

    List<TaskEntity> list;

    public TaskListAdapter(){
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.task_item, parent, false);
        return new TaskItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TaskItemViewHolder taskItemViewHolder = (TaskItemViewHolder) holder;
        taskItemViewHolder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<TaskEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }
}
