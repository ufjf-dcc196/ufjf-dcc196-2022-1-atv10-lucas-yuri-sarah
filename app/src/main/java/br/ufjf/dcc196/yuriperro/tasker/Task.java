package br.ufjf.dcc196.yuriperro.tasker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name="name")
    private String name;

    @ColumnInfo(name="status")
    private Boolean status;

    @ColumnInfo(name="userId")
    private Long userId;

    public Task() {
        this(null,null,false,null);
    }

    public Task(Long id,String name,Boolean status,Long userId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.id = id;
        this.userId = userId;
    }

    public Task(String name,Boolean status,Long userId) {
        this.name = name;
        this.status = status;
        this.id = id;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
