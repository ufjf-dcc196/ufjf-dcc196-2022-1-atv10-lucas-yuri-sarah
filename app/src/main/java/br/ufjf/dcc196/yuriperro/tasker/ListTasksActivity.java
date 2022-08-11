package br.ufjf.dcc196.yuriperro.tasker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CompoundButton;

import java.util.ArrayList;

public class ListTasksActivity extends AppCompatActivity {
    RecyclerView recyclerTasks;
    private ItemTouchHelper.SimpleCallback touchHelperCallback;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tasks);

        this.initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerTasks = findViewById(R.id.recyclerViewTasks);

        // layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerTasks.setLayoutManager(layoutManager);

        // task adapter
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task(1L,"Tarefa 1",false,null));
        tasks.add(new Task(2L,"Tarefa 2",false,null));
        tasks.add(new Task(3L,"Tarefa 3",true,null));

        taskAdapter = new TaskAdapter(tasks);
        recyclerTasks.setAdapter(taskAdapter);

        // switch change handler
        taskAdapter.setSwitchChangeListener(new TaskAdapter.OnSwitchChangeListener() {
            @Override
            public void onSwitchChange(CompoundButton button, boolean isChecked, int position) {
                tasks.get(position).setStatus(isChecked);
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
                tasks.remove(position);
                taskAdapter.notifyItemRemoved(position);
            }
        };
        new ItemTouchHelper(touchHelperCallback).attachToRecyclerView(recyclerTasks);



    }
}