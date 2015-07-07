package com.oknion.conceptmap.services;

import java.util.List;

import com.oknion.conceptmap.Model.Classes;
import com.oknion.conceptmap.Model.Friends;
import com.oknion.conceptmap.Model.User;

public interface UserService {

	public boolean addUser(User user);

	public User getUser(String username);

	public List<User> getListUsers();

	public boolean updateUser(User user);

	public Classes getClasses(String classId);

	public List<User> getUsersByName(String name);

	public boolean addFriend(Friends friend);

	public Friends getFriends(Integer friendId);

	public boolean updateFriends(Friends friend);

	public boolean deleteFriends(Friends friend);

	// public List<Friends> getFiendsConfirmed(String username);
}
