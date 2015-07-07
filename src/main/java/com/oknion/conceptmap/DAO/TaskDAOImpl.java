package com.oknion.conceptmap.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.oknion.conceptmap.Model.Task;

@Repository
public class TaskDAOImpl implements TaskDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean addTask(Task task) {

		try {
			Session session = sessionFactory.getCurrentSession();
			session.persist(task);
			System.out.println("After Add Task, Transaction is now "
					+ session.getTransaction().getLocalStatus() + "_"
					+ session.getTransaction().isActive());
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public Task getTaskById(int taskId) {
		Session session = sessionFactory.getCurrentSession();
		Task task = (Task) session.get(Task.class, taskId);
		return task;
	}

	@Override
	public boolean updateTask(Task task) {
		try {
			this.sessionFactory.getCurrentSession().update(task);
			return true;
		} catch (Exception e) {
			System.out.println("Update error!");
			return false;
		}
	}

}
