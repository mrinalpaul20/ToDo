package in.cshare.android.kisantodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Task> tasks;
    private TaskOCmplteListener listener;

    public TasksAdapter(Context context, TaskOCmplteListener listener) {
        this.context = context;
        this.listener = listener;
        tasks = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.laypout_task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskTv.setText(task.getTask());
        if (task.isCompleted())
            holder.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        else
            holder.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> list) {
        tasks.clear();
        tasks.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_layout)
        LinearLayout itemLayout;
        @BindView(R.id.task_tv)
        TextView taskTv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnLongClick(R.id.item_layout)
        void onLognClickItemLayout() {
            Task task = tasks.get(getAdapterPosition());
            if (!task.isCompleted())
                listener.completetTask(task);
        }
    }
}
