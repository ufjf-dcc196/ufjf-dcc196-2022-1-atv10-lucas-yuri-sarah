package br.ufjf.dcc196.yuriperro.tasker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    private TextView email;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(getApplicationContext());

        email = findViewById(R.id.textInputEmailLogin);
        password = findViewById(R.id.textInputPasswordLogin);
    }

    public void handleButtonLogin(View view){
        User hasUser = db.userDao().login(email.getText().toString(), password.getText().toString());

        if(hasUser == null){
            Toast.makeText(this, "Email ou senha incorretos!", Toast.LENGTH_LONG).show();
        }else {

            Intent intent = new Intent(MainActivity.this, ListTasksActivity.class);
            intent.putExtra("userId", hasUser.getId().toString());
            startActivity(intent);
        }
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