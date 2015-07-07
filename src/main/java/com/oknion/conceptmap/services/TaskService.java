package com.oknion.conceptmap.services;

import com.oknion.conceptmap.Model.StoreTaskInfo;
import com.oknion.conceptmap.Model.Task;
import com.oknion.conceptmap.Model.User;

public interface TaskService {

	public boolean addTask(StoreTaskInfo taskinfo, User ownUser);

	public Task getTaskById(int taskId);

	public boolean updateTask(StoreTaskInfo storeTaskInfo);
}
