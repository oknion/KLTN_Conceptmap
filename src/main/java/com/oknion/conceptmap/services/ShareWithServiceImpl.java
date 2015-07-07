package com.oknion.conceptmap.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oknion.conceptmap.DAO.ShareWithDAO;
import com.oknion.conceptmap.DAO.UserDAO;
import com.oknion.conceptmap.Model.Conceptmap;
import com.oknion.conceptmap.Model.Sharewith;
import com.oknion.conceptmap.Model.User;

@Service
@Transactional
public class ShareWithServiceImpl implements ShareWithService {

	private UserDAO userDao;

	private ConceptMapService conceptMapService;

	private ShareWithDAO shareWithDao;

	private Sharewith shareWith;

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public void setConceptMapService(ConceptMapService conceptMapService) {
		this.conceptMapService = conceptMapService;
	}

	public void setShareWithDao(ShareWithDAO shareWithDao) {
		this.shareWithDao = shareWithDao;
	}

	@Override
	public void shareWith(int cmId, String listUsername) {

		String[] userArray;
		userArray = listUsername.split(",");
		Conceptmap cm = conceptMapService.getConceptMapbyId(cmId);
		if (cm != null) {
			User user = null;
			for (String string : userArray) {
				user = userDao.getUser(string.trim());
				if (user != null) {
					shareWith = new Sharewith(user, cm, new Date());
					shareWithDao.share(shareWith);
				}
				user = null;
			}
		}

	}

	@Override
	public List<String> getListSharewiths(int cmId) {
		List<Sharewith> sharewiths = shareWithDao.getListSharewiths(cmId);
		List<String> listUsernameString = new ArrayList<String>();
		for (Sharewith sharewith : sharewiths) {
			listUsernameString.add(sharewith.getUser().getUserId());
		}
		return listUsernameString;
	}

	@Override
	public boolean deleteShare(Sharewith sharewith) {
		return this.shareWithDao.deleteShare(sharewith);
	}

	@Override
	public Sharewith getSharewith(String userId, int cmId) {
		return this.shareWithDao.getSharewith(userId, cmId);
	}

}
