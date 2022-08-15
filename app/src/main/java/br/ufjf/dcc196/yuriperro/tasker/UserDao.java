package br.ufjf.dcc196.yuriperro.tasker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void create(User user);

    @Query("SELECT * FROM task")
    List<User> getAll();

    @Query("SELECT * FROM task WHERE id=:id LIMIT 1")
    User getById(Long id);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
