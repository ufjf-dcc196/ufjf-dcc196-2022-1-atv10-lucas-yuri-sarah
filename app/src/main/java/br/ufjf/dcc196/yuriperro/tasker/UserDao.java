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

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE id=:id LIMIT 1")
    User getById(Long id);

    @Query("SELECT * FROM user WHERE email=:email LIMIT 1")
    User getByEmail(String email);

    @Query("SELECT * FROM user WHERE email=:email and password=:password LIMIT 1")
    User login(String email, String password);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
