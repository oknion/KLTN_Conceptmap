package com.oknion.conceptmap.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oknion.conceptmap.DAO.TaskDAO;
import com.oknion.conceptmap.DAO.UserDAO;
import com.oknion.conceptmap.Model.Classes;
import com.oknion.conceptmap.Model.Conceptmap;
import com.oknion.conceptmap.Model.StoreTaskInfo;
import com.oknion.conceptmap.Model.Task;
import com.oknion.conceptmap.Model.User;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

	private ConceptMapService conceptMapService;
	private UserDAO userDao;
	private TaskDAO taskDao;

	public void setTaskDao(TaskDAO taskDao) {
		this.taskDao = taskDao;
	}

	public void setConceptMapService(ConceptMapService conceptMapService) {
		this.conceptMapService = conceptMapService;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean addTask(StoreTaskInfo taskinfo, User ownUser) {

		Set<User> setUsers = new HashSet<User>();
		Conceptmap cm = conceptMapService.getConceptMapbyId((int) taskinfo
				.getCmId());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date deadline;
		String[] usernameArr = taskinfo.getListTaskUsername().split(",");

		System.out.print(taskinfo.getListTaskUsername());
		String forclass = "";
		try {
			User user;
			deadline = df.parse(taskinfo.getDatetime());
			for (String username : usernameArr) {
				String getuserString = username.trim();
				Classes classes = userDao.getClasses(getuserString);
				if (classes == null) {
					if (getuserString != "") {
						user = userDao.getUser(getuserString);
						if (user != null) {
							setUsers.add(user);
							forclass += getuserString + ",";
						}
					}
				} else {
					forclass += classes.getClassId() + ",";
					for (User userInClass : classes.getUsers()) {
						setUsers.add(userInClass);
					}
				}
			}
			System.out.print(setUsers.size());

		} catch (ParseException e) {
			System.out.print("can not parse datetime");

			return false;
		}
		Task task = new Task();
		task.setForclass(forclass);
		task.setConceptmap(cm);
		task.setDeadLine(deadline);
		task.setTaskName(taskinfo.getTaskName());
		task.setTaskDescription(taskinfo.getTaskDes());
		task.setUsers(setUsers);
		task.setOwnUser(ownUser);
		try {
			taskDao.addTask(task);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public Task getTaskById(int taskId) {
		return taskDao.getTaskById(taskId);
	}

	@Override
	public boolean updateTask(StoreTaskInfo storeTaskInfo) {
		Set<User> setUsers = new HashSet<User>();

		Task task = taskDao.getTaskById(storeTaskInfo.getTaskId());
		task.setTaskName(storeTaskInfo.getTaskName());
		task.setTaskDescription(storeTaskInfo.getTaskDes());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date deadline;
		String forclass = "";
		String[] usernameArr = storeTaskInfo.getListTaskUsername().split(",");

		try {
			User user;
			deadline = df.parse(storeTaskInfo.getDatetime());
			task.setDeadLine(deadline);
			for (String username : usernameArr) {
				String getuserString = username.trim();
				Classes classes = userDao.getClasses(getuserString);
				if (classes == null) {
					if (getuserString != "") {
						user = userDao.getUser(getuserString);
						if (user != null) {
							setUsers.add(user);
							forclass += getuserString + ",";
						}
					}
				} else {
					forclass += classes.getClassId() + ",";
					for (User userInClass : classes.getUsers()) {
						setUsers.add(userInClass);
					}
				}
			}
			System.out.print(setUsers.size());

		} catch (ParseException e) {
			System.out.print("can not parse datetime");

			return false;
		}
		task.setForclass(forclass);
		task.setUsers(setUsers);
		taskDao.updateTask(task);

		return true;
	}
}
