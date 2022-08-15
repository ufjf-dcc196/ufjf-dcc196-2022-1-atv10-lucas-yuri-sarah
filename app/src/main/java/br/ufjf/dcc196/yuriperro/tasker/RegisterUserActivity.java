package br.ufjf.dcc196.yuriperro.tasker;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterUserActivity extends AppCompatActivity {
    private AppDatabase db;
    private TextView name;
    private TextView email;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        db = AppDatabase.getInstance(getApplicationContext());

        name = findViewById(R.id.textInputName);
        email = findViewById(R.id.textInputEmail);
        password = findViewById(R.id.textInputPassword);
    }

    public void handleCreateAccount(View view){
        User user = new User(name.getText().toString(), email.getText().toString(), password.getText().toString());
        db.userDao().create(user);
    }
}
