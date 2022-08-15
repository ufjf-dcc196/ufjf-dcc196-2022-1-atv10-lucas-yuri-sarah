package br.ufjf.dcc196.yuriperro.tasker;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

    private Boolean hasBlankFields(){
        if(name.getText().toString().length() == 0) return true;
        if(email.getText().toString().length() == 0) return true;
        if(password.getText().toString().length() == 0) return true;

        return false;
    }

    public void handleCreateAccount(View view){
        User hasUser = db.userDao().getByEmail(email.getText().toString());

        Boolean result = hasBlankFields();

        if(result)  Toast.makeText(this, "Não podem haver campos em branco!", Toast.LENGTH_LONG).show();
        else if( hasUser.getId() != null){
            Toast.makeText(this, "Email já cadastrado!", Toast.LENGTH_LONG).show();
        } else {
            User user = new User(name.getText().toString(), email.getText().toString(), password.getText().toString());
            db.userDao().create(user);

            Toast.makeText(this, "Usuário criado com sucesso!", Toast.LENGTH_LONG).show();

            finish();
        }
    }

    public void handleBack(View view){
        finish();
    }
}
