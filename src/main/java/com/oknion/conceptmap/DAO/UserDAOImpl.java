package com.oknion.conceptmap.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.oknion.conceptmap.Model.Classes;
import com.oknion.conceptmap.Model.Friends;
import com.oknion.conceptmap.Model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@Override
	public boolean addUser(User user) {

		Session session = this.sessionFactory.getCurrentSession();
		if (this.getUser(user.getUserId()) != null) {
			return false;
		}
		try {
			session.persist(user);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public User getUser(String userId) {
		Session session = this.sessionFactory.getCurrentSession();
		User p = (User) session.get(User.class, userId);
		System.out.println("Person loaded successfully, Person details=" + p);
		return p;

	}

	@Override
	public List<User> getListUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM User";
		Query query = session.createQuery(hql);
		List<?> results = query.list();
		System.out.println(results.size());
		return (List<User>) results;
	}

	@Override
	public boolean updateUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(user);
		return false;
	}

	@Override
	public Classes getClasses(String classId) {
		Session session = this.sessionFactory.getCurrentSession();
		Classes classes = (Classes) session.get(Classes.class, classId);
		System.out.println("Classes loaded successfully, Classes details="
				+ classes);
		return classes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM User U WHERE U.fullName LIKE :name OR U.mssv LIKE :name OR U.email LIKE :name";
		Query query = session.createQuery(hql);
		query.setParameter("name", "%" + name + "%");

		List<User> results = (List<User>) query.list();
		System.out.println(results.size());
		return results;
	}

	@Override
	public boolean addFriend(Friends friend) {

		Session session = this.sessionFactory.getCurrentSession();

		try {
			session.persist(friend);

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Friends getFriends(Integer friendId) {
		Session session = this.sessionFactory.getCurrentSession();
		Friends p = (Friends) session.get(Friends.class, friendId);
		System.out.println("Friends loaded successfully, Friend details=" + p);
		return p;
	}

	@Override
	public boolean updateFriends(Friends friend) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.update(friend);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteFriends(Friends friend) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			session.delete(friend);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
