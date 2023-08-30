package lk.test.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lk.test.myapplication.R;
import lk.test.myapplication.models.task.TaskEntity;
import lk.test.myapplication.utilities.ItemClickListener;
import lk.test.myapplication.viewholder.TaskItemViewHolder;

public class TaskListAdapter extends RecyclerView.Adapter {

    private List<TaskEntity> list;
    private ItemClickListener clickListener;
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
        taskItemViewHolder.setData(list.get(position), clickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<TaskEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener clickListener){
        this.clickListener = clickListener;
    }

    public TaskEntity getTaskAt(int position){
        return list.get(position);
    }

    public void updateTaskAt(int position, TaskEntity newTask){
        this.list.set(position, newTask);
        notifyItemChanged(position);
    }
}
