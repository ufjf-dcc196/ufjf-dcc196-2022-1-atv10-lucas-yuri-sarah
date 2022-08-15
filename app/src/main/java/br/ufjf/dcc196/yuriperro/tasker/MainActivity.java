package br.ufjf.dcc196.yuriperro.tasker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(getApplicationContext());
    }

    public void handleButtonLogin(View view){

    }

    public void handleButtonRegister(View view){
        Intent intent = new Intent(MainActivity.this, RegisterUserActivity.class);
        startActivity(intent);
    }

    public void navigateToListTasks(View source){
        Intent intent = new Intent(MainActivity.this, ListTasksActivity.class);
        startActivity(intent);
    }
}