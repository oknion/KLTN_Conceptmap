package com.oknion.conceptmap.Model;

// Generated Oct 21, 2014 3:41:47 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable {

	private String userId;
	private Classes classes;
	private String email;
	private String fullName;
	private String password;
	private String role = "student";
	private Boolean status;
	private Boolean sex;
	private String s3bucketId;
	private String mssv;
	private String khoa;

	private Set<Conceptmap> conceptmaps = new HashSet<Conceptmap>(0);
	private Set<Task> tasks = new HashSet<Task>(0);
	private Set<Notification> notifications = new HashSet<Notification>(0);
	private Set<Sharewith> sharewiths = new HashSet<Sharewith>(0);
	private Set<Task> ownTasks = new HashSet<Task>(0);

	private Set<Friends> friends = new HashSet<Friends>(0);
	private Set<Friends> friends1 = new HashSet<Friends>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "desUser")
	public Set<Friends> getFriends1() {
		return friends1;
	}

	public void setFriends1(Set<Friends> friends1) {
		this.friends1 = friends1;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sourceUser")
	public Set<Friends> getFriends() {
		return friends;
	}

	public void setFriends(Set<Friends> friends) {
		this.friends = friends;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ownUser")
	public Set<Task> getOwnTasks() {
		return ownTasks;
	}

	public void setOwnTasks(Set<Task> ownTasks) {
		this.ownTasks = ownTasks;
	}

	public User() {
	}

	public User(String userId, String email, String fullName, String password) {
		this.userId = userId;
		this.email = email;
		this.fullName = fullName;
		this.password = password;
	}

	public User(String userId, Classes classes, String email, String fullName,
			String password, String role, Boolean status,
			Set<Conceptmap> conceptmaps, Set<Task> tasks,
			Set<Notification> notifications, Set<Sharewith> sharewiths) {
		this.userId = userId;
		this.classes = classes;
		this.email = email;
		this.fullName = fullName;
		this.password = password;
		this.role = role;
		this.status = status;
		this.conceptmaps = conceptmaps;
		this.tasks = tasks;
		this.notifications = notifications;
		this.sharewiths = sharewiths;
	}

	@Id
	@Column(name = "userId", unique = true, nullable = false, length = 50)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@ManyToOne
	@JoinColumn(name = "classId")
	@Cascade({ CascadeType.ALL })
	public Classes getClasses() {
		return this.classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "fullName", length = 50)
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "role", length = 20)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "sex")
	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	@Column(name = "s3bucketId")
	public String getS3bucketId() {
		return s3bucketId;
	}

	public void setS3bucketId(String s3bucketId) {
		this.s3bucketId = s3bucketId;
	}

	@Column(name = "status")
	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@Cascade({ CascadeType.ALL })
	public Set<Conceptmap> getConceptmaps() {
		return this.conceptmaps;
	}

	public void setConceptmaps(Set<Conceptmap> conceptmaps) {
		this.conceptmaps = conceptmaps;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
	@Cascade({ CascadeType.ALL })
	public Set<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@Cascade({ CascadeType.ALL })
	public Set<Notification> getNotifications() {
		return this.notifications;
	}

	public void setNotifications(Set<Notification> notifications) {
		this.notifications = notifications;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@Cascade({ CascadeType.ALL })
	public Set<Sharewith> getSharewiths() {
		return this.sharewiths;
	}

	public void setSharewiths(Set<Sharewith> sharewiths) {
		this.sharewiths = sharewiths;
	}

	@Column(name = "mssv")
	public String getMssv() {
		return mssv;
	}

	public void setMssv(String mssv) {
		this.mssv = mssv;
	}

	@Column(name = "khoa")
	public String getKhoa() {
		return khoa;
	}

	public void setKhoa(String khoa) {
		this.khoa = khoa;
	}

}
