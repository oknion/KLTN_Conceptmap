package com.oknion.conceptmap;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.oknion.conceptmap.DAO.MyUserDetails;
import com.oknion.conceptmap.Model.Document;
import com.oknion.conceptmap.services.DocumentService;
import com.oknion.conceptmap.utils.AmazonUtils;

@Controller
public class FileUploadController {
	Document ufile;

	private DocumentService documentService;

	@Autowired
	@Qualifier(value = "documentService")
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public FileUploadController() {
		System.out.println("init RestController");

	}

	public UserDetails getCurrentUserDetails() {
		return (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}

	@RequestMapping("/download/{documentId}")
	public String downloadForError(
			@PathVariable("documentId") Integer documentId,
			HttpServletResponse response) {
		MyUserDetails userDetails = (MyUserDetails) getCurrentUserDetails();
		Document doc = documentService.getDocumentbyId(documentId);
		// doc.setBytes(null);
		AmazonUtils.s3Object2Document(doc, doc.getS3BucketId());

		try {
			response.setHeader("Content-Disposition", "inline;filename=\""
					+ doc.getName() + "\"");

			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getType());
			response.setContentLength(doc.getLength());
			FileCopyUtils.copy(doc.getBytes(), response.getOutputStream());
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping("/download/{documentCmId}/{ccId}")
	public String download(@PathVariable String documentCmId,
			@PathVariable int ccId, HttpSession session,
			HttpServletResponse response) {

		Set<Document> documents = (Set<Document>) session
				.getAttribute(documentCmId);
		MyUserDetails userDetails = (MyUserDetails) getCurrentUserDetails();
		Document doc = this.getDocFromListbyCcId(ccId, documents);
		if (doc != null) {
			AmazonUtils.s3Object2Document(doc, userDetails.getS3bucketId());
		}
		try {
			response.setHeader("Content-Disposition", "inline;filename=\""
					+ doc.getName() + "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getType());
			response.setContentLength(doc.getLength());
			FileCopyUtils.copy(doc.getBytes(), response.getOutputStream());
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = { "/s3/getlistfileandfolder" }, method = RequestMethod.POST)
	public @ResponseBody Map<String, List<String>> s3getlistfileandfolder(
			@RequestParam(value = "path") String path) {
		System.out.println(path);
		if (path.startsWith("/")) {
			path = path.substring(1, path.length());
		}
		MyUserDetails userDetails = (MyUserDetails) getCurrentUserDetails();
		List<String> listFile1 = AmazonUtils.getListFile(
				userDetails.getS3bucketId(), path, AmazonUtils.CLIENT);
		List<String> listFile = new ArrayList<String>();
		for (String string : listFile1) {
			if (!string.endsWith("/")) {
				listFile.add(string);
			}
		}
		List<String> listFol = AmazonUtils.getListFol(
				userDetails.getS3bucketId(), path, AmazonUtils.CLIENT);

		Map<String, List<String>> listFileandFoList = new HashMap<String, List<String>>();
		listFileandFoList.put("listFile", listFile);
		listFileandFoList.put("listFol", listFol);
		return listFileandFoList;

	}

	@RequestMapping(value = { "/uploadFromS3" }, method = RequestMethod.POST)
	public @ResponseBody String uploadFromS3(
			@RequestParam(value = "s3KeyId") String s3KeyId,
			@RequestParam(value = "cmId") String cmId,
			@RequestParam(value = "ccId") int ccId, HttpSession session) {
		MyUserDetails userDetails = (MyUserDetails) getCurrentUserDetails();
		System.out.println("get document from " + cmId + "ccId:" + ccId
				+ "s3KeyId:" + s3KeyId);
		Document ufile = documentService.getDocByS3KeyId(s3KeyId,
				userDetails.getS3bucketId());
		ufile.setDocumentCcId(ccId);
		Set<Document> sessionDocuments = (Set<Document>) session
				.getAttribute(cmId);

		if (sessionDocuments != null) {
			System.out.println("Num Doc before add ufile: "
					+ sessionDocuments.size());
			Set<Document> documents = new HashSet<Document>();
			for (Document document : sessionDocuments) {
				System.out.println("Document id:" + document.getDocumentCcId()
						+ "\n Document name:" + document.getName());

				if (document.getDocumentCcId() != ufile.getDocumentCcId()) {
					System.out.println("Remove one doc");
					documents.add(document);
				} else if (document.getDocumentId() != null) {
					ufile.setDocumentId(document.getDocumentId());
				}
			}
			sessionDocuments.clear();
			for (Document document : documents) {
				sessionDocuments.add(document);
			}

			System.out
					.println("Num Doc before remove doc for concept own ufile: "
							+ sessionDocuments.size());
			sessionDocuments.add(ufile);
			System.out.println("Num Doc after add ufile: "
					+ sessionDocuments.size());
			for (Document document : sessionDocuments) {
				System.out.println("Document id:" + document.getDocumentCcId()
						+ "\n Document name:" + document.getName());
			}

			session.putValue(cmId, sessionDocuments);

		} else {
			sessionDocuments = new HashSet<Document>();
			sessionDocuments.add(ufile);
			for (Document document : sessionDocuments) {
				System.out.println("Document id:" + document.getDocumentCcId()
						+ "\n Document name:" + document.getName());
			}
			session.putValue(cmId, sessionDocuments);
		}

		return "";
	}

	@SuppressWarnings({ "deprecation" })
	@RequestMapping(value = "fileUploadController/upload", method = RequestMethod.POST)
	public @ResponseBody String upload(MultipartHttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		System.out.println("Upload from local file");
		MyUserDetails userDetails = (MyUserDetails) getCurrentUserDetails();

		Iterator<String> itr = request.getFileNames();

		MultipartFile mpf = request.getFile(itr.next());

		System.out.println(mpf.getOriginalFilename() + " uploaded!");
		ufile = new Document();
		try {
			// just temporary save file info into ufile
			ufile.setLength(mpf.getBytes().length);
			ufile.setBytes(mpf.getBytes());
			ufile.setType(mpf.getContentType());
			ufile.setName(mpf.getOriginalFilename());
			ufile.setDocumentName(request.getParameter("inputDocumentName"));
			ufile.setDocumentCcId(Integer.parseInt(request
					.getParameter("documentCcId")));
			ufile.setS3BucketId(userDetails.getS3bucketId());

			Set<Document> sessionDocuments = (Set<Document>) session
					.getAttribute(request.getParameter("documentCmId"));

			if (sessionDocuments != null) {
				System.out.println("Num Doc before add ufile: "
						+ sessionDocuments.size());
				Set<Document> documents = new HashSet<Document>();
				for (Document document : sessionDocuments) {
					System.out.println("Document id:"
							+ document.getDocumentCcId() + "\n Document name:"
							+ document.getName());

					if (document.getDocumentCcId() != ufile.getDocumentCcId()) {
						System.out.println("Remove one doc");
						documents.add(document);
					} else if (document.getDocumentId() != null) {
						ufile.setDocumentId(document.getDocumentId());
					}
				}
				sessionDocuments.clear();
				for (Document document : documents) {
					sessionDocuments.add(document);
				}

				System.out
						.println("Num Doc before remove doc for concept own ufile: "
								+ sessionDocuments.size());
				sessionDocuments.add(ufile);
				System.out.println("Num Doc after add ufile: "
						+ sessionDocuments.size());
				for (Document document : sessionDocuments) {
					System.out.println("Document id:"
							+ document.getDocumentCcId() + "\n Document name:"
							+ document.getName());
				}

				session.putValue(request.getParameter("documentCmId"),
						sessionDocuments);

			} else {
				sessionDocuments = new HashSet<Document>();
				sessionDocuments.add(ufile);
				for (Document document : sessionDocuments) {
					System.out.println("Document id:"
							+ document.getDocumentCcId() + "\n Document name:"
							+ document.getName());
				}
				session.putValue(request.getParameter("documentCmId"),
						sessionDocuments);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "<img src='http://localhost:8080/spring-mvc-file-upload/rest/cont/get/ ' />";
	}

	@RequestMapping(value = "/get/document", method = RequestMethod.POST)
	public @ResponseBody int getDocument(
			@RequestParam(value = "cmId") String cmId,
			@RequestParam(value = "ccId") int ccId, HttpSession session) {
		System.out.print(cmId + "_" + ccId);
		Set<Document> documents = (Set<Document>) session.getAttribute(cmId);
		Document document = this.getDocFromListbyCcId(ccId, documents);
		if (document != null) {
			return document.getDocumentCcId();
		} else {
			return 0;
		}

	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/delete/document", method = RequestMethod.POST)
	public @ResponseBody int getDocument(
			@RequestParam(value = "ccId") int ccId,
			@RequestParam(value = "documentCmId") String documentCmId,
			HttpSession session) {
		@SuppressWarnings("unchecked")
		Set<Document> sessionDocuments = (Set<Document>) session
				.getAttribute(documentCmId);
		System.out
				.println("Before delete document: " + sessionDocuments.size());
		if (sessionDocuments != null) {
			Set<Document> documents = new HashSet<Document>();
			for (Document document : sessionDocuments) {
				if (document.getDocumentCcId() != ccId) {
					documents.add(document);
				}
			}
			sessionDocuments.clear();
			for (Document document : documents) {
				sessionDocuments.add(document);
			}
			System.out.println("After delete document: "
					+ sessionDocuments.size());
			session.putValue(documentCmId, sessionDocuments);
		}

		return 0;
	}

	private Document getDocFromListbyCcId(int ccId, Set<Document> documents) {
		if (documents != null) {
			for (Document document : documents) {
				if (document.getDocumentCcId() == ccId) {
					return document;
				}
			}
		}
		return null;
	}

}
