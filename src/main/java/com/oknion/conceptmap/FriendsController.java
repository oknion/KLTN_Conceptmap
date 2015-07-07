package com.oknion.conceptmap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oknion.conceptmap.Model.Friends;
import com.oknion.conceptmap.Model.User;
import com.oknion.conceptmap.Model.basicUser;
import com.oknion.conceptmap.services.UserService;

@Controller
public class FriendsController {

	private UserService userService;

	// private ConceptMapService conceptMapService;
	// private TaskService taskService;
	//
	// @Autowired(required = true)
	// @Qualifier(value = "taskService")
	// public void setTaskService(TaskService taskService) {
	// this.taskService = taskService;
	// }

	// @Autowired(required = true)
	// @Qualifier(value = "conceptMapService")
	// public void setConceptMapService(ConceptMapService conceptMapService) {
	// this.conceptMapService = conceptMapService;
	// }

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = { "/getListFriends" }, method = RequestMethod.GET)
	public String getListFriends(Model model) {
		User currentUser = this.userService.getUser(getCurrentUserDetails()
				.getUsername());
		Set<Friends> friends = currentUser.getFriends();
		System.out.println(friends.size());
		Set<Friends> unconfirmFriends = currentUser.getFriends1();
		model.addAttribute("friends", friends);
		model.addAttribute("unconfirmFriends", unconfirmFriends);

		return "friends";
	}

	@RequestMapping(value = { "/findFriend" }, method = RequestMethod.POST)
	public @ResponseBody List<basicUser> findFriends(
			@RequestParam("friendName") String friendName) {
		List<basicUser> basicUsers = new ArrayList<basicUser>();
		Set<Friends> friends = new HashSet<Friends>();
		String currentUsernameString = getCurrentUserDetails().getUsername();
		friends = userService.getUser(currentUsernameString).getFriends();
		for (User user : userService.getUsersByName(friendName)) {
			if (friends.size() >= 1) {
				for (Friends friends2 : friends) {
					if (user.getUserId() != friends2.getDesUser().getUserId()
							&& user.getUserId() != friends2.getSourceUser()
									.getUserId()
							&& user.getUserId() != currentUsernameString) {
						basicUsers
								.add(new basicUser(user.getUserId(), user
										.getFullName(), user.getMssv(), user
										.getEmail()));
					}
				}
			} else {
				if (user.getUserId() != currentUsernameString) {
					basicUsers.add(new basicUser(user.getUserId(), user
							.getFullName(), user.getMssv(), user.getEmail()));
				}
			}

		}
		return basicUsers;
	}

	@RequestMapping(value = { "/addFriend" }, method = RequestMethod.GET)
	public String addFriend(@RequestParam("friendId") String friendId) {
		Friends friends = new Friends();
		friends.setSourceUser(userService.getUser(getCurrentUserDetails()
				.getUsername()));
		friends.setDesUser(userService.getUser(friendId));
		userService.addFriend(friends);
		return "redirect:getListFriends";
	}

	@RequestMapping(value = "/confirmAddFriend", method = RequestMethod.GET)
	public String confirmAddFriend(@RequestParam("friendId") String friendId) {
		Friends friends = userService.getFriends(Integer.parseInt(friendId));
		friends.setStatus(true);
		userService.updateFriends(friends);
		return "redirect:getListFriends";
	}

	@RequestMapping(value = "/deleteAddFriend", method = RequestMethod.GET)
	public String deleteAddFriend(@RequestParam("friendId") String friendId) {
		Friends friends = userService.getFriends(Integer.parseInt(friendId));
		userService.deleteFriends(friends);
		return "redirect:getListFriends";
	}

	public UserDetails getCurrentUserDetails() {
		return (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}
}
