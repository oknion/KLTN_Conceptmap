package com.oknion.conceptmap;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.services.s3.model.S3Object;
import com.oknion.conceptmap.DAO.MyUserDetails;
import com.oknion.conceptmap.Model.Document;
import com.oknion.conceptmap.services.DocumentService;
import com.oknion.conceptmap.utils.AmazonUtils;

@Controller
public class FileManager {
	private DocumentService documentService;

	@Autowired
	@Qualifier(value = "documentService")
	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public UserDetails getCurrentUserDetails() {
		return (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
	}

	@RequestMapping("/fileManager/download")
	public String downloadFile(@RequestParam String path,
			HttpServletResponse response) {
		System.out.println(path);
		MyUserDetails userDetails = (MyUserDetails) getCurrentUserDetails();
		S3Object s3Object = AmazonUtils.getS3Object(
				userDetails.getS3bucketId(), path);
		System.out.println(s3Object.getKey());
		String[] filename = s3Object.getKey().split("/");
		try {
			response.setHeader("Content-Disposition", "inline;filename=\""
					+ filename[filename.length - 1] + "\"");

			OutputStream out = response.getOutputStream();
			response.setContentType(s3Object.getObjectMetadata()
					.getContentType());
			response.setContentLength((int) s3Object.getObjectMetadata()
					.getContentLength());
			FileCopyUtils.copy(s3Object.getObjectContent(),
					response.getOutputStream());
			out.flush();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/fileManager/upload", method = RequestMethod.POST)
	public @ResponseBody String upload(MultipartHttpServletRequest request,
			HttpServletResponse response, HttpSession session) {

		// 0. notice, we have used MultipartHttpServletRequest
		MyUserDetails userDetails = (MyUserDetails) getCurrentUserDetails();
		// 1. get the files from the request object
		Iterator<String> itr = request.getFileNames();

		MultipartFile mpf = request.getFile(itr.next());

		System.out.println(mpf.getOriginalFilename() + " uploaded!");
		Document ufile = new Document();
		try {
			// just temporary save file info into ufile
			ufile.setLength(mpf.getBytes().length);
			ufile.setBytes(mpf.getBytes());
			ufile.setType(mpf.getContentType());
			ufile.setName(mpf.getOriginalFilename());
			ufile.setDocumentName(request.getParameter("inputDocumentName"));
			ufile.setS3KeyIdString(request.getParameter("currentPath")
					+ mpf.getOriginalFilename());
			ufile.setS3BucketId(userDetails.getS3bucketId());
			System.out.println(request.getParameter("currentPath")
					+ mpf.getOriginalFilename());
			System.out.println(request.getParameter("currentPath"));
			documentService.addDocument(ufile);
			AmazonUtils.upload2S3(ufile, userDetails.getS3bucketId());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Uploaded";

	}

	@RequestMapping(value = "/fileManager/newFolder", method = RequestMethod.POST)
	public @ResponseBody int newFolder(
			@RequestParam(value = "path") String path,
			@RequestParam(value = "folName") String folName) {
		MyUserDetails userDetails = (MyUserDetails) getCurrentUserDetails();
		AmazonUtils.createFolder(userDetails.getS3bucketId(), path + folName,
				AmazonUtils.CLIENT);
		return 1;
	}

	@RequestMapping(value = "/fileManager/deleFile", method = RequestMethod.POST)
	public String deleteFile(@RequestParam(value = "path") String path) {
		MyUserDetails userDetails = (MyUserDetails) getCurrentUserDetails();
		AmazonUtils.deleteFile(userDetails.getS3bucketId(), path,
				AmazonUtils.CLIENT);
		return "fileManager";
	}

	@RequestMapping(value = "/fileManager/deleFol", method = RequestMethod.POST)
	public String deleteFol(@RequestParam(value = "path") String path) {
		MyUserDetails userDetails = (MyUserDetails) getCurrentUserDetails();
		AmazonUtils.deleteFolder(userDetails.getS3bucketId(), path,
				AmazonUtils.CLIENT);
		return "fileManager";
	}

	@RequestMapping(value = "/fileManager", method = RequestMethod.GET)
	public String fileManager(ModelMap model, @RequestParam String path) {
		MyUserDetails userDetails = (MyUserDetails) getCurrentUserDetails();
		List<String> listFile = AmazonUtils.getListFile(
				userDetails.getS3bucketId(), path, AmazonUtils.CLIENT);
		List<String> stringFileReturn = new ArrayList<String>();
		List<String> stringFolReturn = new ArrayList<String>();
		for (String string : listFile) {
			if (string.equals(path)) {

			} else {
				String[] strings = string.split("/");
				System.out.println(strings.length);
				stringFileReturn.add(strings[strings.length - 1]);
			}

		}

		List<String> listFol = AmazonUtils.getListFol(
				userDetails.getS3bucketId(), path, AmazonUtils.CLIENT);
		for (String string : listFol) {
			String[] strings = string.split("/");
			stringFolReturn.add(strings[strings.length - 1]);
		}

		model.put("listFile", stringFileReturn);
		model.put("path", path);
		model.put("listFol", stringFolReturn);
		return "fileManager";
	}
}
