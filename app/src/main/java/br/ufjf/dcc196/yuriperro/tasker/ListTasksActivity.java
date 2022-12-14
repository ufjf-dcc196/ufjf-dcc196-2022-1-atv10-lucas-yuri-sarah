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
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListTasksActivity extends AppCompatActivity {
    RecyclerView recyclerTasks;
    TextView textViewWelcome;

    TaskAdapter taskAdapter;
    private ItemTouchHelper.SimpleCallback touchHelperCallback;
    List<Task> tasks = new ArrayList<Task>();

    private User loggedUser = null;

    private AppDatabase db;

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

        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewWelcome.setText("Bem-vindo " + loggedUser.getName()+ "!");

        this.initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerTasks = findViewById(R.id.recyclerViewTasks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerTasks.setLayoutManager(layoutManager);

        // task adapter
        tasks = db.taskDao().getAllByUserId(loggedUser.getId());
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
            public void onClickChange(View v, int position) {
                Task taskNameUpdate = tasks.get(position);
                updateTask(taskNameUpdate, position);

                if(!recyclerTasks.isComputingLayout()) {
                    taskAdapter.notifyItemChanged(position);
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
        final View customLayout = getLayoutInflater().inflate(R.layout.layout_task_dialog,null);
        builder.setView(customLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText = customLayout.findViewById(R.id.editText);
                Task newTask = new Task(editText.getText().toString(),false,loggedUser.getId());
                tasks.add(newTask);
                db.taskDao().create(newTask);
                taskAdapter.notifyDataSetChanged();
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

    private void updateTask(Task task, int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Tarefa");
        final View customLayout = getLayoutInflater().inflate(R.layout.layout_task_dialog,null);
        builder.setView(customLayout);
        EditText editText = customLayout.findViewById(R.id.editText);
        editText.setText(task.getName());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                task.setName(editText.getText().toString());
                tasks.set(position, task);
                db.taskDao().update(task);
                taskAdapter.notifyDataSetChanged();

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

    public void logout(View source){
        loggedUser = null;
        finish();
    }
}