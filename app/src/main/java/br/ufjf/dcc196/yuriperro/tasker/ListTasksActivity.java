package br.ufjf.dcc196.yuriperro.tasker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerTasks.setLayoutManager(layoutManager);

        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(new Task(1L,"Tarefa 1",false,null));
        tasks.add(new Task(2L,"Tarefa 2",false,null));
        tasks.add(new Task(3L,"Tarefa 3",true,null));

        taskAdapter = new TaskAdapter(tasks);
        recyclerTasks.setAdapter(taskAdapter);


        touchHelperCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.UP) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                tasks.remove(position);
                taskAdapter.notifyItemChanged(position);
            }
        };
        new ItemTouchHelper(touchHelperCallback).attachToRecyclerView(recyclerTasks);
    }
}