package br.ufjf.dcc196.yuriperro.tasker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// quando for adicionar a entidade usuário {Task.class}, {User.class}

@Database(entities = {Task.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "tasker-db";
    private static AppDatabase INSTANCE;

    public abstract TaskDao taskDao();
    public abstract UserDao userDao();

    public static AppDatabase getInstance(Context ctx){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(
                    ctx.getApplicationContext(),
                    AppDatabase.class,
                    DB_NAME
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

}
