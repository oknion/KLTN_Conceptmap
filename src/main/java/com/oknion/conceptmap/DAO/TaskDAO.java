package com.oknion.conceptmap.DAO;

import com.oknion.conceptmap.Model.Task;

public interface TaskDAO {

	public boolean addTask(Task task);

	public Task getTaskById(int taskId);

	public boolean updateTask(Task task);
}
