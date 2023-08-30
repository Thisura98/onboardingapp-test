package lk.test.myapplication.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import lk.test.myapplication.R;
import lk.test.myapplication.models.task.TaskEntity;
import lk.test.myapplication.models.task.TaskStatus;
import lk.test.myapplication.utilities.ItemClickListener;

public class TaskItemViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTitle;
    private TextView tvDueDate;
    private TextView tvStatus;

    public TaskItemViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvDueDate = itemView.findViewById(R.id.tvDueDate);
        tvStatus = itemView.findViewById(R.id.tvStatus);
    }

    public void setData(TaskEntity entity, ItemClickListener clickListener){
        tvTitle.setText(entity.title);
        tvDueDate.setText(entity.getDueDateAsString());
        setTaskStatus(entity.status);
        setClickListener(clickListener);
    }

    private void setStatusUI(String status, @DrawableRes int drawableResource){
        Context context = itemView.getContext();
        tvStatus.setText(status);
        tvStatus.setBackground(ContextCompat.getDrawable(context, drawableResource));
    }

    private void setTaskStatus(TaskStatus status){
        switch(status){
            case PENDING:
                setStatusUI("Pending", R.drawable.status_pending_drawable);
                break;
            case IN_PROGRESS:
                setStatusUI("In progress", R.drawable.status_inprogress_drawable);
                break;
            case COMPLETED:
                setStatusUI("Completed", R.drawable.status_complete_drawable);
                break;
        }
    }

    private void setClickListener(ItemClickListener clickListener){
        if (clickListener != null){
            itemView.setOnClickListener(view -> clickListener.onItemClick(view, getAdapterPosition()));
        }
        else{
            itemView.setOnClickListener(null);
        }
    }
}
