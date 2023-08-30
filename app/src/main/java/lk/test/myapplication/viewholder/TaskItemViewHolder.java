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

    public void setData(TaskEntity entity){
        tvTitle.setText(entity.title);
        tvDueDate.setText(entity.getDueDateAsString());

        switch(entity.status){
            case PENDING:
                setStatus("Pending", R.drawable.status_pending_drawable);
                break;
            case IN_PROGRESS:
                setStatus("In progress", R.drawable.status_inprogress_drawable);
                break;
            case COMPLETED:
                setStatus("Completed", R.drawable.status_complete_drawable);
                break;
        }
    }

    private void setStatus(String status, @DrawableRes int drawableResource){
        Context context = itemView.getContext();
        tvStatus.setText(status);
        tvStatus.setBackground(ContextCompat.getDrawable(context, drawableResource));
    }
}
