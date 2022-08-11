package br.ufjf.dcc196.yuriperro.tasker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks;
    private OnTaskClickListener listener;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTaskName;
        private Switch switchTaskStatus;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
            switchTaskStatus = itemView.findViewById(R.id.switchTaskStatus);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);

        View taskView = inflater.inflate(R.layout.layout_task_item, parent, false);
        TaskViewHolder holder = new TaskViewHolder(taskView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textViewTaskName.setText(task.getName());
        holder.switchTaskStatus.setChecked(task.getStatus());

        if (task.getStatus()) {
            holder.switchTaskStatus.setText("Concluído");
        } else {
            holder.switchTaskStatus.setText("A fazer");
        }

    }

    public interface OnTaskClickListener {
        void onTaskClick(View source, int position);
    }

    public OnTaskClickListener getTaskClickListener() {
        return listener;
    }

    public void setTaskClickListener(OnTaskClickListener listener) {
        this.listener = listener;
    }

}