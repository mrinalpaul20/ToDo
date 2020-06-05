package in.cshare.android.kisantodo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class AddTaskDialog extends Dialog {
    @BindView(R.id.task_et)
    EditText taskEt;
    private AddTaskListener listener;

    public AddTaskDialog(@NonNull Context context, AddTaskListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_task);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setLayout(MATCH_PARENT, WRAP_CONTENT);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_btn)
    void onClickAddTaskBtn() {
        String task = taskEt.getText().toString().trim();
        if (!task.isEmpty()) {
            listener.addTask(new Task(task));
            dismiss();
        } else
            Toast.makeText(getContext(), "Please Enter a Task!", Toast.LENGTH_SHORT).show();
    }
}