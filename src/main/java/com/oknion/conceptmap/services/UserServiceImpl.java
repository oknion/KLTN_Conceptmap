package com.oknion.conceptmap.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oknion.conceptmap.DAO.UserDAO;
import com.oknion.conceptmap.Model.Classes;
import com.oknion.conceptmap.Model.Friends;
import com.oknion.conceptmap.Model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	private UserDAO userDao;

	@Override
	public boolean addUser(User user) {
		return userDao.addUser(user);

	}

	@Override
	public User getUser(String username) {
		User user = userDao.getUser(username);

		return user;
	}

	@Override
	public List<User> getListUsers() {
		return this.userDao.getListUsers();
	}

	@Override
	public boolean updateUser(User user) {
		userDao.updateUser(user);
		return false;
	}

	@Override
	public Classes getClasses(String classId) {
		return userDao.getClasses(classId);
	}

	@Override
	public List<User> getUsersByName(String name) {
		// TODO Auto-generated method stub
		return userDao.getUsersByName(name);
	}

	@Override
	public boolean addFriend(Friends friend) {
		// TODO Auto-generated method stub
		return this.userDao.addFriend(friend);
	}

	@Override
	public Friends getFriends(Integer friendId) {
		return this.userDao.getFriends(friendId);
	}

	@Override
	public boolean updateFriends(Friends friend) {
		// TODO Auto-generated method stub
		return this.userDao.updateFriends(friend);
	}

	@Override
	public boolean deleteFriends(Friends friend) {
		return this.userDao.deleteFriends(friend);
	}

	// @Override
	// public List<Friends> getFiendsConfirmed(String username) {
	// // TODO Auto-generated method stub
	// return null;
	// }

}
