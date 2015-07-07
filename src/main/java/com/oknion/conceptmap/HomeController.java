package com.oknion.conceptmap;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oknion.conceptmap.Model.Ccrelationship;
import com.oknion.conceptmap.Model.CcrelationshipPoints;
import com.oknion.conceptmap.Model.Concept;
import com.oknion.conceptmap.Model.Conceptmap;
import com.oknion.conceptmap.Model.Document;
import com.oknion.conceptmap.Model.Error;
import com.oknion.conceptmap.Model.Friends;
import com.oknion.conceptmap.Model.Task;
import com.oknion.conceptmap.Model.User;
import com.oknion.conceptmap.services.ConceptMapService;
import com.oknion.conceptmap.services.TaskService;
import com.oknion.conceptmap.services.UserService;
import com.oknion.conceptmap.utils.AmazonUtils;
import com.oknion.conceptmap.utils.CompareConceptmap;
import com.oknion.conceptmap.utils.RandomString;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);
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

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = { "/login", "/" })
	public ModelAndView login(@RequestParam(required = false) boolean error) {
		ModelAndView mav = new ModelAndView("login");
		if (error)
			mav.addObject("error", error);
		return mav;
	}

	@RequestMapping(value = { "/register" })
	public String register() {

		return "register";
	}

	// @RequestMapping(value = { "/register/facebook" }, method =
	// RequestMethod.POST)
	// public ModelAndView registerWithFace(WebRequest request) {
	// ModelAndView mav = null;
	// Connection<?> connection = ProviderSignInUtils.getConnection(request);
	// User user;
	// if (connection == null) {
	// } else {
	// UserProfile userProfile = connection.fetchUserProfile();
	// user = new User(userProfile.getUsername(), userProfile.getEmail(),
	// userProfile.getName(), "22222");
	// }
	//
	// return mav;
	//
	// }

	@RequestMapping(value = { "/registerAccount" }, method = RequestMethod.POST)
	public ModelAndView registerAccount(
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "sex") String sex,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "fullName") String fullName,
			@RequestParam(value = "classes") String classes,
			@RequestParam(value = "khoa") String khoa,
			@RequestParam(value = "studentId") String studentId) {

		User user = null;

		user = new User(userId, email, fullName, password);
		if (sex == "M") {
			user.setSex(true);
		} else {
			user.setSex(false);
		}
		user.setMssv(studentId);
		user.setClasses(userService.getClasses(classes));
		user.setKhoa(khoa);
		boolean temp = false;
		do {
			user.setS3bucketId(UUID.randomUUID().toString());
			temp = AmazonUtils.createBucket(AmazonUtils.CLIENT,
					user.getS3bucketId());
		} while (!temp);
		ModelAndView mav;
		if (userService.addUser(user)) {

			mav = new ModelAndView("login");
		} else {
			mav = new ModelAndView("register");
			mav.addObject("error", true);
		}
		return mav;
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/listmap", method = RequestMethod.GET)
	public String listMap(Model model) {
		UserDetails userDetails = getCurrentUserDetails();
		String role = userDetails.getAuthorities().toArray()[0].toString();
		User currentUser = userService.getUser(userDetails.getUsername());

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

		if (role.equals("ROLE_STUDENT")) {

			System.out.println("Before get user:" + userDetails.getUsername());
			User user = userService.getUser(userDetails.getUsername());
			System.out.println("After get user");
			Set<Task> tasks = new HashSet<Task>();
			for (Task task : user.getTasks()) {

				try {
					if (task.getConceptmaps().size() > 0)
						tasks.add(task);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}

			}
			model.addAttribute("listCM", tasks);
			System.out.println("After get Tasks");

			Set<Conceptmap> nonTaskCM = new HashSet<Conceptmap>();
			for (Conceptmap conceptmap : user.getConceptmaps()) {
				if (conceptmap.getTask() == null) {
					nonTaskCM.add(conceptmap);
				}
			}
			model.addAttribute("nonTaskCM", nonTaskCM);
			return "listmapStudent";
		} else if (role.equals("ROLE_TEACHER")) {
			User user = userService.getUser(userDetails.getUsername());
			model.addAttribute("listCM", user.getConceptmaps());
			return "listmapTeacher";
		} else {
			return "redirect:indexAdmin";

		}

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model, HttpSession session) {
		UserDetails userDetails = getCurrentUserDetails();
		String role = userDetails.getAuthorities().toArray()[0].toString();
		if (role.equals("ROLE_STUDENT")) {
			model.addAttribute("cmTempId",
					session.getId() + RandomString.randomstring(10, 10));
			return "indexStudent";
		} else if (role.equals("ROLE_TEACHER")) {
			model.addAttribute("cmTempId",
					session.getId() + RandomString.randomstring(10, 10));
			return "indexTeacher";
		} else {
			return "redirect:indexAdmin";
		}

	}

	@RequestMapping(value = "/dotask{taskId}", method = RequestMethod.GET)
	public String doTask(Model model, HttpSession session,
			@PathVariable int taskId) {
		UserDetails userDetails = getCurrentUserDetails();
		String role = userDetails.getAuthorities().toArray()[0].toString();

		if (role.equals("ROLE_STUDENT")) {
			String randomString = RandomString.randomstring(10, 10);
			Task task = taskService.getTaskById(taskId);
			if (task.getDeadLine().compareTo(new Date()) < 0) {
				return "listtaskStudent";
			}
			model.addAttribute("taskInfo", task);
			model.addAttribute("cmTempId", session.getId() + randomString);
			return "indexStudent";
		} else if (role.equals("ROLE_TEACHER")) {
			return "listmapTeacher";
		} else {
			return "redirect:indexAdmin";
		}

	}

	@RequestMapping(value = "/listshare", method = RequestMethod.GET)
	public String listShare(Model model) {
		UserDetails userDetails = getCurrentUserDetails();
		String role = userDetails.getAuthorities().toArray()[0].toString();
		if (role.equals("ROLE_STUDENT") || role.equals("ROLE_TEACHER")) {
			User user = userService.getUser(userDetails.getUsername());
			model.addAttribute("listShare", user.getSharewiths());
			return "listshare";
		} else {
			return "redirect:indexAdmin";
		}

	}

	@RequestMapping(value = "/listshareDetail/{cmid}", method = RequestMethod.GET, headers = "Accept=application/json")
	public String listShareDetail(@PathVariable int cmid, Model model) {
		UserDetails userDetails = getCurrentUserDetails();
		String role = userDetails.getAuthorities().toArray()[0].toString();
		if (role.equals("ROLE_STUDENT") || role.equals("ROLE_TEACHER")) {
			model.addAttribute("loadCmId", cmid);
			return "shareDetail";
		} else {
			return "redirect:indexAdmin";
		}
	}

	@RequestMapping(value = "/accountInfo", method = RequestMethod.GET)
	public String accountInfo(ModelMap model) {
		userService.getUser(getCurrentUserDetails().getUsername());
		model.put("user",
				userService.getUser(getCurrentUserDetails().getUsername()));

		return "accountInfo";
	}

	@RequestMapping(value = "/accountInfo", method = RequestMethod.POST)
	public String accountInfo(
			@RequestParam(value = "fullName") String fullName,
			@RequestParam(value = "email") String email) {
		User user = userService.getUser(getCurrentUserDetails().getUsername());
		user.setFullName(fullName);
		user.setEmail(email);
		userService.updateUser(user);
		return "redirect:accountInfo";
	}

	@RequestMapping(value = "/changePass", method = RequestMethod.GET)
	public String changePass(Map<String, Object> model) {

		return "changePass";
	}

	@RequestMapping(value = "/changePass", method = RequestMethod.POST)
	public ModelAndView changePass(
			@RequestParam(value = "inputPassword") String inputPassword,
			@RequestParam(value = "inputNewPassword") String inputNewPassword,
			@RequestParam(value = "inputNewPassword1") String inputNewPassword1) {
		ModelAndView mav = new ModelAndView();
		System.out.println(inputPassword + ";" + inputNewPassword + ";"
				+ inputNewPassword1);
		if (!inputNewPassword.equals(inputNewPassword1)
				|| !inputPassword.equals(getCurrentUserDetails().getPassword())) {
			mav.addObject("error", true);
			mav.setViewName("changePass");
		} else {
			User user = userService.getUser(getCurrentUserDetails()
					.getUsername());
			user.setPassword(inputNewPassword);
			userService.updateUser(user);
			mav.setViewName("changePass");
		}

		return mav;
	}

	@RequestMapping(value = "/listmapDetail/{id}", method = RequestMethod.GET)
	public String listmapDetail(@PathVariable int id, Model model) {

		UserDetails userDetails = getCurrentUserDetails();
		String role = userDetails.getAuthorities().toArray()[0].toString();

		if (role.equals("ROLE_STUDENT")) {
			User user = userService.getUser(userDetails.getUsername());
			Set<Conceptmap> setCm = new HashSet<Conceptmap>();

			for (Conceptmap cm : user.getConceptmaps()) {
				try {
					if (cm.getTask().getTaskId() == id) {
						setCm.add(cm);
					}
				} catch (Exception e) {
				}

			}
			System.out.print(setCm.size());
			model.addAttribute("listCM", setCm);
		}

		return "listmapDetail";
	}

	@RequestMapping(value = "/indexAddAccount", method = RequestMethod.GET)
	public String indexAddAccount() {
		return "indexAddAccount";
	}

	@RequestMapping(value = "/indexAdmin", method = RequestMethod.GET)
	public String indexAdmin(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("listUser", this.userService.getListUsers());

		return "indexAdmin";
	}

	@RequestMapping(value = "/edituserinfo", method = RequestMethod.GET)
	public String editUserInfo(Model model,
			@RequestParam("userId") String userId) {
		model.addAttribute("user", userService.getUser(userId));
		model.addAttribute("listUser", this.userService.getListUsers());

		return "indexAdmin";
	}

	@RequestMapping(value = "/edituserinfo", method = RequestMethod.POST)
	public String editUserInfoConfirm(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "fullName") String fullName,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "role") String role,
			@RequestParam(value = "classes") String classes,
			@RequestParam(value = "studentId") String studentId) {
		User user = userService.getUser(username);
		if (user != null) {
			user.setFullName(fullName);
			user.setEmail(email);
			user.setRole(role);
			user.setClasses(userService.getClasses(classes));
			user.setMssv(studentId);
			userService.updateUser(user);
		}
		return "redirect:indexAdmin";
	}

	@RequestMapping(value = "/gojs", method = RequestMethod.GET)
	public String goJs() {
		return "GoJs";
	}

	@RequestMapping(value = "/getError/{cmId}", method = RequestMethod.GET)
	public @ResponseBody Set<Error> getErrors(@PathVariable int cmId) {

		Conceptmap compareCm = conceptMapService.getConceptMapbyId(cmId);
		Conceptmap sourceCm = compareCm.getTask().getConceptmap();

		Set<Error> errors = CompareConceptmap.compare(sourceCm, compareCm);
		Error ero = new Error();
		ero.setConceptmap(compareCm);
		ero.setDescrip(String.valueOf(compareCm.getScore()));
		ero.setName("score");

		errors.add(ero);
		for (Error error : errors) {
			System.out.print(error.getName());
		}
		return errors;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/saveConceptmapTeacher", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Conceptmap addConceptmapTeacher(
			@RequestBody Conceptmap jsonDemo, HttpSession session)
			throws IOException {
		User user = userService.getUser(getCurrentUserDetails().getUsername());
		Set<Document> sesionDocuments = null;
		sesionDocuments = (Set<Document>) session.getAttribute(jsonDemo
				.getCmSessionTempId());

		if (jsonDemo.getCmId() == 0) {
			if (sesionDocuments != null) {
				for (Concept concept : jsonDemo.getConcepts()) {

					System.out.print(sesionDocuments.size());
					Set<Document> setDocuments = new HashSet<Document>();
					for (Document document : sesionDocuments) {
						System.out.print(document.getDocumentName());
						int ccKeyId = concept.getKeyId().intValue();
						if (ccKeyId == document.getDocumentCcId()) {
							Document documentTemp = new Document();
							documentTemp.setBytes(document.getBytes());
							documentTemp.setConcept(concept);
							documentTemp.setDocumentName(document.getName());
							documentTemp.setLength(document.getLength());
							documentTemp.setName(document.getName());
							documentTemp.setType(documentTemp.getType());
							documentTemp.setDocumentCcId(document
									.getDocumentCcId());
							documentTemp
									.setS3BucketId(document.getS3BucketId());
							if (document.getS3KeyIdString() != null) {
								documentTemp.setS3KeyIdString(document
										.getS3KeyIdString());
							} else {
								documentTemp.setS3KeyIdString(RandomString
										.randomstring(1, 5)
										+ documentTemp.getName());
								AmazonUtils.upload2S3(documentTemp,
										user.getS3bucketId());
							}
							documentTemp.setBytes(null);
							setDocuments.add(documentTemp);
						}
					}
					concept.setDocuments(setDocuments);
					System.out.print(concept.getDocuments().size());
				}

			} else {
				System.out.println("No document!");
			}
			if (jsonDemo.getCcrelationships() != null) {
				for (Ccrelationship rela : jsonDemo.getCcrelationships()) {
					if (rela.getText() == null) {
						rela.setText("");

					}
					rela.setConceptmap(jsonDemo);

					for (int i = 0; i < 8; i++) {
						CcrelationshipPoints ccrelationshipPoints = new CcrelationshipPoints();
						ccrelationshipPoints.setCcrelationship(rela);
						ccrelationshipPoints.setPoints(rela.getPoints()[i]);
						ccrelationshipPoints.setOrders(i);
						rela.getCcrelationshipPointses().add(
								ccrelationshipPoints);

					}
				}
			}
			for (Concept concept : jsonDemo.getConcepts()) {
				concept.setConceptmap(jsonDemo);
				if (concept.getCcName() == null) {
					concept.setCcName("");
				}
			}

			jsonDemo.setUser(user);
			jsonDemo.setDateCreate(new Date());
			conceptMapService.addConceptMap(jsonDemo);

		} else {

			Conceptmap conceptmapBeforeUpdate = conceptMapService
					.getConceptMapbyId(jsonDemo.getCmId());

			for (Ccrelationship rela : jsonDemo.getCcrelationships()) {
				if (rela.getText() == null) {
					rela.setText("");

				}
				rela.setConceptmap(conceptmapBeforeUpdate);
			}
			for (Concept concept : jsonDemo.getConcepts()) {
				concept.setConceptmap(conceptmapBeforeUpdate);
				if (concept.getCcName() == null) {
					concept.setCcName("");
				}
			}

			for (Concept concept : conceptmapBeforeUpdate.getConcepts()) {

				for (Concept concept2 : jsonDemo.getConcepts()) {

					if (concept2.getKeyId().equals(concept.getKeyId())) {
						System.out.print("set ccId");
						concept2.setCcId(concept.getCcId());

					}
				}
			}

			for (Ccrelationship ccrelationship2 : jsonDemo.getCcrelationships()) {
				for (Ccrelationship ccrelationship : conceptmapBeforeUpdate
						.getCcrelationships()) {

					if (ccrelationship.getFrom() == ccrelationship2.getFrom()
							&& ccrelationship.getTo() == ccrelationship2
									.getTo()) {
						ccrelationship2.setCcrelaId(ccrelationship
								.getCcrelaId());
						System.out.print("Set relaId ");
					}

				}
			}
			conceptmapBeforeUpdate.getConcepts().clear();
			conceptmapBeforeUpdate.getCcrelationships().clear();
			for (Concept concept : jsonDemo.getConcepts()) {
				conceptmapBeforeUpdate.getConcepts().add(concept);
			}
			for (Ccrelationship ccrelationship : jsonDemo.getCcrelationships()) {
				conceptmapBeforeUpdate.getCcrelationships().add(ccrelationship);
			}

			// Set document for concept
			if (sesionDocuments != null && sesionDocuments.size() != 0) {
				for (Concept concept : conceptmapBeforeUpdate.getConcepts()) {
					concept.getDocuments().clear();
					Set<Document> setDocuments = new HashSet<Document>();
					for (Document document : sesionDocuments) {
						System.out.print(document.getDocumentCcId() + "_");
						int ccKeyId = concept.getKeyId().intValue();
						if (ccKeyId == document.getDocumentCcId()) {
							Document documentTemp = new Document();
							documentTemp.setBytes(document.getBytes());
							documentTemp.setConcept(concept);
							documentTemp.setDocumentName(document.getName());
							documentTemp.setLength(document.getLength());
							documentTemp.setName(document.getName());
							documentTemp.setType(documentTemp.getType());
							documentTemp.setDocumentCcId(document
									.getDocumentCcId());
							documentTemp
									.setS3BucketId(document.getS3BucketId());
							if (document.getDocumentId() != null) {
								documentTemp.setDocumentId(document
										.getDocumentId());
							}

							if (document.getS3KeyIdString() != null) {
								documentTemp.setS3KeyIdString(document
										.getS3KeyIdString());
							} else {
								documentTemp.setS3KeyIdString(RandomString
										.randomstring(1, 5)
										+ documentTemp.getName());
								AmazonUtils.upload2S3(documentTemp,
										user.getS3bucketId());
							}
							documentTemp.setBytes(null);
							setDocuments.add(documentTemp);
						}
					}
					for (Document document : setDocuments) {
						concept.getDocuments().add(document);
					}
				}

			}

			conceptmapBeforeUpdate.setImgString(jsonDemo.getImgString());
			conceptmapBeforeUpdate.setCmName(jsonDemo.getCmName());
			conceptmapBeforeUpdate.setDescription(jsonDemo.getDescription());
			conceptmapBeforeUpdate.setDateCreate(new Date());

			// Set CcrelationshipPoint
			for (Ccrelationship rela : conceptmapBeforeUpdate
					.getCcrelationships()) {
				rela.getCcrelationshipPointses().clear();
				for (int i = 0; i < 8; i++) {
					CcrelationshipPoints ccrelationshipPoints = new CcrelationshipPoints();
					ccrelationshipPoints.setCcrelationship(rela);
					ccrelationshipPoints.setPoints(rela.getPoints()[i]);
					ccrelationshipPoints.setOrders(i);
					rela.getCcrelationshipPointses().add(ccrelationshipPoints);
				}
			}

			conceptMapService.updateConceptmap(conceptmapBeforeUpdate);
			System.out.println(jsonDemo.getConcepts().size());
			System.out.println(jsonDemo.getCcrelationships().size());
			for (Concept concept : conceptmapBeforeUpdate.getConcepts()) {
				for (Document document : concept.getDocuments()) {
					System.out.println("Document ccid:"
							+ document.getDocumentCcId() + "\n document id: "
							+ document.getDocumentId());
				}
			}
			System.out.print("Conceptmap's updated!");

		}

		return jsonDemo;

	}

	@RequestMapping(value = "/gojspost", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Set<Error> addConceptMap(
			@RequestBody Conceptmap jsonDemo, HttpSession session, Model model)
			throws IOException {

		Set<com.oknion.conceptmap.Model.Error> errors = null;
		User user = userService.getUser(getCurrentUserDetails().getUsername());
		@SuppressWarnings("unchecked")
		Set<Document> sesionDocuments = (Set<Document>) session
				.getAttribute(jsonDemo.getCmSessionTempId());
		if (sesionDocuments != null) {
			for (Concept concept : jsonDemo.getConcepts()) {

				System.out.print(sesionDocuments.size());
				Set<Document> setDocuments = new HashSet<Document>();
				for (Document document : sesionDocuments) {
					System.out.print(document.getDocumentName());

					if (concept.getKeyId() == document.getDocumentCcId()) {
						Document documentTemp = new Document();
						documentTemp.setBytes(document.getBytes());
						documentTemp.setConcept(concept);
						documentTemp.setDocumentName(document.getName());
						documentTemp.setLength(document.getLength());
						documentTemp.setName(document.getName());
						documentTemp.setType(documentTemp.getType());
						documentTemp
								.setDocumentCcId(document.getDocumentCcId());
						documentTemp.setS3BucketId(document.getS3BucketId());
						if (document.getS3KeyIdString() != null) {
							documentTemp.setS3KeyIdString(document
									.getS3KeyIdString());
						} else {
							documentTemp.setS3KeyIdString(RandomString
									.randomstring(1, 5)
									+ documentTemp.getName());
							AmazonUtils.upload2S3(documentTemp,
									user.getS3bucketId());
						}
						documentTemp.setBytes(null);
						setDocuments.add(documentTemp);
					}
				}
				concept.setDocuments(setDocuments);
				System.out.print(concept.getDocuments().size());
			}
		}

		for (Ccrelationship rela : jsonDemo.getCcrelationships()) {
			if (rela.getText() == null) {
				rela.setText("");

			}
			rela.setConceptmap(jsonDemo);
		}
		for (Concept concept : jsonDemo.getConcepts()) {
			concept.setConceptmap(jsonDemo);
		}
		if (jsonDemo.getCmId() == 0) {
			jsonDemo.setUser(user);
			jsonDemo.setDateCreate(new Date());
			System.out.println("Before get task");

			if (jsonDemo.getTaskId() != null && jsonDemo.getTaskId() != 0) {
				Task task = taskService.getTaskById(jsonDemo.getTaskId());
				System.out.println(" Get " + task.getTaskName()
						+ " from session!");
				jsonDemo.setTask(task);
				errors = CompareConceptmap.compare(task.getConceptmap(),
						jsonDemo);

				System.out.println(jsonDemo.getScore());
			}

			Error ero = new Error();
			ero.setConceptmap(jsonDemo);
			ero.setDescrip(String.valueOf(jsonDemo.getScore()));
			ero.setName("score");
			errors.add(ero);

			conceptMapService.addConceptMap(jsonDemo);

			System.out.println("After add conceptmap");
		}

		return errors;

	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String editConceptMap(@PathVariable int id, HttpSession session,
			Model model) {
		Set<Document> documents = new HashSet<Document>();
		String randomString = RandomString.randomstring(10, 10);
		Conceptmap cm = conceptMapService.getConceptMapbyId(id);
		for (Concept concept : cm.getConcepts()) {
			for (Document document : concept.getDocuments()) {
				documents.add(document);
			}
		}
		System.out.println(cm.getDescription());
		model.addAttribute("cmTempId", session.getId() + randomString);
		model.addAttribute("loadCmId", id);
		session.putValue(session.getId() + randomString, documents);

		return "indexStudent";
	}

	@RequestMapping(value = "/getcm/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Conceptmap getConceptMap(@PathVariable int id) {
		Conceptmap cm = conceptMapService.getConceptMapbyId(id);
		return cm;
	}

	@RequestMapping(value = "/deletecm/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody Conceptmap deleteConceptMap(@PathVariable int id) {
		Conceptmap cm = conceptMapService.getConceptMapbyId(id);
		if (cm != null) {
			conceptMapService.deleteConceptmap(cm);
		}
		return cm;
	}

	public UserDetails getCurrentUserDetails() {
		return (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}

}
