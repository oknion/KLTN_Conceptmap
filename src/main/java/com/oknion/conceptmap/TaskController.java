package com.oknion.conceptmap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oknion.conceptmap.Model.Conceptmap;
import com.oknion.conceptmap.Model.Document;
import com.oknion.conceptmap.Model.Error;
import com.oknion.conceptmap.Model.Friends;
import com.oknion.conceptmap.Model.ReportInfor;
import com.oknion.conceptmap.Model.ReportInforDetail;
import com.oknion.conceptmap.Model.StoreTaskInfo;
import com.oknion.conceptmap.Model.Task;
import com.oknion.conceptmap.Model.User;
import com.oknion.conceptmap.services.ConceptMapService;
import com.oknion.conceptmap.services.TaskService;
import com.oknion.conceptmap.services.UserService;
import com.oknion.conceptmap.utils.CompareConceptmap;

@Controller
public class TaskController {

	private UserService userService;
	private ConceptMapService conceptMapService;
	private TaskService taskService;

	@Autowired(required = true)
	@Qualifier(value = "taskService")
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired(required = true)
	@Qualifier(value = "conceptMapService")
	public void setConceptMapService(ConceptMapService conceptMapService) {
		this.conceptMapService = conceptMapService;
	}

	@RequestMapping(value = { "getTaskDocuments" }, method = RequestMethod.POST)
	public @ResponseBody Set<Document> getTaskDocuments(
			@RequestParam("cmId") String cmId) {
		return this.conceptMapService.getDocumentByCM(Integer.parseInt(cmId));
	}

	@RequestMapping(value = { "updateTask" }, method = RequestMethod.POST)
	public @ResponseBody int updateTask(@RequestBody StoreTaskInfo storeTaskInfo) {
		taskService.updateTask(storeTaskInfo);

		return 0;
	}

	@RequestMapping("/report")
	public String report() {

		return "report";

	}

	@RequestMapping(value = { "getReport" }, method = RequestMethod.GET)
	public String getReport(@RequestParam("taskId") int taskId, Model model) {
		Task task = taskService.getTaskById(taskId);
		model.addAttribute("task", task);
		Set<Conceptmap> conceptmaps = task.getConceptmaps();
		System.out.println(conceptmaps.size());
		Map<String, List<Conceptmap>> mapConceptmap = new HashMap<String, List<Conceptmap>>();

		for (Conceptmap conceptmap : conceptmaps) {
			if (mapConceptmap.containsKey(conceptmap.getUser().getUserId())) {
				mapConceptmap.get(conceptmap.getUser().getUserId()).add(
						conceptmap);
			} else {
				List<Conceptmap> conceptmaps2 = new ArrayList<Conceptmap>();
				conceptmaps2.add(conceptmap);
				mapConceptmap.put(conceptmap.getUser().getUserId(),
						conceptmaps2);
			}
		}
		Map<String, List<ReportInforDetail>> repMap = new HashMap<String, List<ReportInforDetail>>();
		List<ReportInfor> reportInfors = new ArrayList<ReportInfor>();
		List<ReportInforDetail> reportInforDetails = new ArrayList<ReportInforDetail>();
		for (String keyString : mapConceptmap.keySet()) {
			int highestscore = 0;
			int lowestscore = 0;
			int count = 0;
			int numErrors = 0;
			int sumscore = 0;
			String fullNameString = null;
			for (Conceptmap conceptmap : mapConceptmap.get(keyString)) {
				count += 1;
				Set<Error> errors = CompareConceptmap.compare(
						task.getConceptmap(), conceptmap);
				numErrors = errors.size();
				sumscore += conceptmap.getScore();
				if (count == 1) {
					lowestscore = conceptmap.getScore();
					highestscore = conceptmap.getScore();
				} else {
					if (highestscore < conceptmap.getScore()) {
						highestscore = conceptmap.getScore();
					}
					if (lowestscore > conceptmap.getScore()) {
						lowestscore = conceptmap.getScore();
					}
				}
				fullNameString = conceptmap.getUser().getFullName();
				ReportInforDetail reportInforDetail = new ReportInforDetail();
				reportInforDetail.setDatetimeDate(conceptmap.getDateCreate());
				reportInforDetail.setNumError(numErrors);
				reportInforDetail.setScore(conceptmap.getScore());
				reportInforDetail.setUsername(keyString);
				reportInforDetails.add(reportInforDetail);

			}
			ReportInfor reportInfor = new ReportInfor();
			reportInfor.setUsername(keyString);
			reportInfor.setFullName(fullNameString);
			reportInfor.setHighestScore(highestscore);
			reportInfor.setLowestScore(lowestscore);
			reportInfor.setNo(count);
			reportInfor.setScore(sumscore / count);
			reportInfors.add(reportInfor);
			repMap.put(
					reportInfor.getUsername() + "," + reportInfor.getFullName()
							+ "," + reportInfor.getHighestScore(),
					reportInforDetails);
			model.addAttribute("reportInfors", reportInfors);
			model.addAttribute("reportInforDetails", reportInforDetails);
			reportInforDetails = new ArrayList<ReportInforDetail>();
		}
		return "report";

	}

	@RequestMapping(value = "/getTaskInfo", method = RequestMethod.POST)
	public @ResponseBody StoreTaskInfo getTaskInfo(
			@RequestParam(value = "taskid") int taskId, Model model) {
		Task task = taskService.getTaskById(taskId);
		StoreTaskInfo storeTaskInfo = new StoreTaskInfo();
		storeTaskInfo.setTaskName(task.getTaskName());
		storeTaskInfo.setTaskDes(task.getTaskDescription());
		String listUsernameString = task.getForclass();

		// String[] classeStrings = task.getForclass().split(",");
		// for (User user : task.getUsers()) {
		// boolean b = false;
		// for (String string : classeStrings) {
		// if (user.getClasses().getClassId().equals(string)) {
		// System.out.println(user.getUserId() + " is in class "
		// + string);
		// b = true;
		// }
		// }
		// if (!b) {
		// listUsernameString += user.getUserId() + ",";
		// }
		// }
		System.out.println(listUsernameString);
		storeTaskInfo.setListTaskUsername(listUsernameString);
		storeTaskInfo.setDatetime(task.getDeadLine().toString());
		return storeTaskInfo;
	}

	@RequestMapping(value = { "/makeTask" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody StoreTaskInfo makeTask(
			@RequestBody StoreTaskInfo taskinfo) {
		UserDetails userDetails = getCurrentUserDetails();

		if (taskinfo.getListTaskUsername().trim() != "") {
			Boolean aBoolean = taskService.addTask(taskinfo,
					userService.getUser(userDetails.getUsername()));
			System.out.println(aBoolean);
		}

		System.out.print(taskinfo.getCmId() + taskinfo.getDatetime());
		return taskinfo;
	}

	@RequestMapping(value = "/listtask", method = RequestMethod.GET)
	public String listTask(Model model) {
		UserDetails userDetails = getCurrentUserDetails();
		String role = userDetails.getAuthorities().toArray()[0].toString();
		if (role.equals("ROLE_STUDENT")) {
			User user = userService.getUser(userDetails.getUsername());
			Set<Task> tasks = user.getTasks();
			for (Task task : tasks) {
				if (task.getDeadLine().compareTo(new Date()) < 0) {
					task.setHethan(true);

				}
			}
			model.addAttribute("listTask", tasks);
			return "listtaskStudent";
		} else if (role.equals("ROLE_TEACHER")) {
			User currentUser = userService.getUser(userDetails.getUsername());
			// set friend

			Set<Friends> friends = currentUser.getFriends();

			Set<Friends> friends1 = new HashSet<Friends>();
			for (Friends friends2 : friends) {
				if (friends2.getStatus()) {
					friends1.add(friends2);
				}
			}
			System.out.println("get " + friends1.size() + " friends");
			model.addAttribute("listFriend", friends1);
			friends1 = new HashSet<Friends>();
			for (Friends friends2 : currentUser.getFriends1()) {
				if (friends2.getStatus()) {
					friends1.add(friends2);
				}
			}
			model.addAttribute("listFriend1", friends1);
			model.addAttribute("listTask", currentUser.getOwnTasks());
			return "listtaskTeacher";
		} else {
			return "redirect:indexAdmin";
		}
	}

	public UserDetails getCurrentUserDetails() {
		return (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}

}
