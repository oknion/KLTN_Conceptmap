package com.oknion.conceptmap.services;

import java.util.List;

import com.oknion.conceptmap.Model.Sharewith;

public interface ShareWithService {

	public void shareWith(int cmId, String listUsername);

	public Sharewith getSharewith(String userId, int cmId);

	public List<String> getListSharewiths(int cmId);

	public boolean deleteShare(Sharewith sharewith);
}
