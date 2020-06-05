package in.cshare.android.kisantodo;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TaskOCmplteListener, AddTaskListener {
    @BindView(R.id.tasks_rv)
    RecyclerView taskRv;
    private TasksAdapter tasksAdapter;
    private TasksDatabase tasksDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setTasksRv();
        setDatabase();
    }

    private void setDatabase() {
        tasksDatabase = Room.databaseBuilder(this, TasksDatabase.class, "tasks-db").build();
        tasksDatabase.getDao().getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                if (tasks != null)
                    tasksAdapter.setTasks(tasks);
            }
        });
    }

    private void setTasksRv() {
        tasksAdapter = new TasksAdapter(this, this);
        taskRv.setLayoutManager(new LinearLayoutManager(this));
        taskRv.setItemAnimator(new DefaultItemAnimator());
        taskRv.setAdapter(tasksAdapter);
    }

    @OnClick(R.id.add_task_fab)
    void onClickAddTaskFab() {
        showAddTaskDialog();
    }

    private void showAddTaskDialog() {
        AddTaskDialog addTaskDialog = new AddTaskDialog(this, this);
        addTaskDialog.show();
    }

    @Override
    public void completetTask(final Task task) {
        AlertDialog.Builder alertDialg = new AlertDialog.Builder(this)
                .setTitle("Mark Task Complete?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        task.setCompleted(true);
                        tasksDatabase.getDao().markTaskCompleted(task);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialg.show();
    }

    @Override
    public void addTask(Task task) {
        tasksDatabase.getDao().insertTask(task);
    }
}
