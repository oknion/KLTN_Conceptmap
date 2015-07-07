package com.oknion.conceptmap.DAO;

import java.util.List;

import com.oknion.conceptmap.Model.Sharewith;

public interface ShareWithDAO {

	public boolean share(Sharewith shareWith);

	public Sharewith getSharewith(String userId, int cmId);

	public boolean deleteShare(Sharewith sharewith);

	public List<Sharewith> getListSharewiths(int cmId);
}
