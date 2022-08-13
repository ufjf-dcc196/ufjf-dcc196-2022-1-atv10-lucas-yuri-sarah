package br.ufjf.dcc196.yuriperro.tasker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

public class ListTasksActivity extends AppCompatActivity {
    RecyclerView recyclerTasks;
    TaskAdapter taskAdapter;
    private ItemTouchHelper.SimpleCallback touchHelperCallback;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);
        db = AppDatabase.getInstance(getApplicationContext());

        this.initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerTasks = findViewById(R.id.recyclerViewTasks);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerTasks.setLayoutManager(layoutManager);

        // task adapter
        Long userId = 2L; // Receber como parametro ta tela de login
        List<Task> tasks = db.taskDao().getAllByUserId(userId);
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
        db.taskDao().create(new Task("Nova tarefa",false,2L));
    }
}