package br.ufjf.dcc196.yuriperro.tasker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListTasksActivity extends AppCompatActivity {
    RecyclerView recyclerTasks;
    TaskAdapter taskAdapter;
    private ItemTouchHelper.SimpleCallback touchHelperCallback;

    private User loggedUser = null;

    private AppDatabase db;

    private String textNewTask = "Tarefa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);
        db = AppDatabase.getInstance(getApplicationContext());

        // receive params
        Bundle bundleExtras = getIntent().getExtras();
        if(bundleExtras != null){
            Long userId = Long.parseLong(bundleExtras.getString("userId"));

            loggedUser = db.userDao().getById(userId);
        }

        this.initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerTasks = findViewById(R.id.recyclerViewTasks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerTasks.setLayoutManager(layoutManager);

        // task adapter
        List<Task> tasks = db.taskDao().getAllByUserId(loggedUser.getId());
        taskAdapter = new TaskAdapter(tasks);
        recyclerTasks.setAdapter(taskAdapter);

        // switch change handler
        taskAdapter.setSwitchChangeListener(new TaskAdapter.OnSwitchChangeListener() {
            @Override
            public void onSwitchChange(CompoundButton button, boolean isChecked, int position) {
                Task taskToUpdate = tasks.get(position);
                taskToUpdate.setStatus(isChecked);
                db.taskDao().update(taskToUpdate);

                if(!recyclerTasks.isComputingLayout()) {
                    taskAdapter.notifyItemChanged(position);
                }
            }

            @Override
            public void onClickChange(View v, int adapterPosition) {
                Task taskNameUpdate = tasks.get(adapterPosition);
                updateTask(taskNameUpdate);
                if(!recyclerTasks.isComputingLayout()) {
                    taskAdapter.notifyItemChanged(adapterPosition);
                }
            }
        });

        // swipe handler
        touchHelperCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Task taskToDelete = tasks.get(position);
                tasks.remove(position);
                db.taskDao().delete(taskToDelete);

                taskAdapter.notifyItemRemoved(position);
            }
        };
        new ItemTouchHelper(touchHelperCallback).attachToRecyclerView(recyclerTasks);

    }

    public void createNewTask(View source){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nova Tarefa");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        input.setHint("Nome da tarefa");
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.taskDao().create(new Task(input.getText().toString(),false,loggedUser.getId()));
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void updateTask(Task task){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Tarefa");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        input.setHint(task.getName());
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task.setName(input.getText().toString());
                db.taskDao().update(task);
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}