package com.oknion.conceptmap.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.oknion.conceptmap.Model.Sharewith;

@Repository
public class ShareWithDAOImpl implements ShareWithDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public boolean share(Sharewith shareWith) {

		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(shareWith);
			System.out.println("After Share Conceptmap, Transaction is now "
					+ session.getTransaction().getLocalStatus() + "_"
					+ session.getTransaction().isActive());
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sharewith> getListSharewiths(int cmId) {
		List<Sharewith> list = this.sessionFactory.getCurrentSession()
				.createQuery("from Sharewith").list();
		List<Sharewith> sharewiths = new ArrayList<Sharewith>();
		for (Sharewith sharewith : list) {
			if (sharewith.getConceptmap().getCmId() == cmId) {
				sharewiths.add(sharewith);
			}
		}
		return sharewiths;
	}

	@Override
	public boolean deleteShare(Sharewith sharewith) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.delete(sharewith);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Sharewith getSharewith(String userId, int cmId) {
		List<Sharewith> list = this.sessionFactory.getCurrentSession()
				.createQuery("from Sharewith").list();

		for (Sharewith sharewith : list) {
			if (sharewith.getConceptmap().getCmId() == cmId
					&& sharewith.getUser().getUserId().equals(userId)) {
				return sharewith;
			}
		}
		return null;
	}

}
