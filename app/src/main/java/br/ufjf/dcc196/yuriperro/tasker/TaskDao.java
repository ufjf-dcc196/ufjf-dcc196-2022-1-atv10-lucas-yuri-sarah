package br.ufjf.dcc196.yuriperro.tasker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void create(Task task);

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE userId=:userId")
    List<Task> getAllByUserId(Long userId);

    @Query("SELECT * FROM task WHERE id=:id LIMIT 1")
    Task getById(Long id);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
