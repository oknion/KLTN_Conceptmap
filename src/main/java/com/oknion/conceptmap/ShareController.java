package com.oknion.conceptmap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oknion.conceptmap.Model.StoreShareInfo;
import com.oknion.conceptmap.services.ShareWithService;

@Controller
public class ShareController {

	private ShareWithService shareWithService;

	@Autowired(required = true)
	@Qualifier(value = "shareWithService")
	public void setShareWithService(ShareWithService shareWithService) {
		this.shareWithService = shareWithService;
	}

	@RequestMapping(value = { "/sharecm" }, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody int shareCM(@RequestBody StoreShareInfo shareinfo) {

		String[] listshareString = shareinfo.getListUsername().split(",");
		List<String> alreadyListshareString = shareWithService
				.getListSharewiths((int) shareinfo.getCmId());
		String newListUsername = "";
		for (String string : alreadyListshareString) {
			boolean temp = false;
			for (String string1 : listshareString) {
				if (string.trim().equals(string1.trim())) {
					temp = true;
				}
			}
			if (!temp) {
				System.out.println("Delete:" + string);
				shareWithService.deleteShare(shareWithService.getSharewith(
						string, (int) shareinfo.getCmId()));
			}
		}
		for (String string1 : listshareString) {
			boolean temp = false;
			for (String string : alreadyListshareString) {
				if (string1.trim().equals(string.trim())) {
					temp = true;
				}
			}
			System.out.println("Add:" + string1);
			if (!temp) {
				newListUsername += string1 + ",";
			}
		}
		System.out.println(newListUsername);
		if (newListUsername.trim() != "") {
			shareWithService.shareWith((int) shareinfo.getCmId(),
					newListUsername);
		}
		System.out.println("return");
		return 0;

	}

	@RequestMapping(value = { "/getListShareUsername" }, method = RequestMethod.POST)
	public @ResponseBody List<String> getListShareUsername(
			@RequestParam(value = "shareCmId") int shareCmId) {

		return shareWithService.getListSharewiths(shareCmId);

	}
}
