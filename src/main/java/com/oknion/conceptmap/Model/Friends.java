package com.oknion.conceptmap.Model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friends")
public class Friends {

	private Integer friendId;
	private User sourceUser;
	private User desUser;
	private Boolean status = false;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "friendid", unique = true, nullable = false)
	public Integer getFriendId() {
		return friendId;
	}

	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}

	@ManyToOne
	@JoinColumn(name = "sourceuserid", nullable = false)
	public User getSourceUser() {
		return sourceUser;
	}

	public void setSourceUser(User sourceUser) {
		this.sourceUser = sourceUser;
	}

	@ManyToOne
	@JoinColumn(name = "desuserid", nullable = false)
	public User getDesUser() {
		return desUser;
	}

	public void setDesUser(User desUser) {
		this.desUser = desUser;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
