package com.todolist;

import java.util.List;

public interface TaskDAO {
    void addTask(Task task);
    void updateTask(Task task);
    void deleteTask(int taskId);
    List<Task> getAllTasks();
}
