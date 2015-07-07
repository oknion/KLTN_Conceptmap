package com.oknion.conceptmap.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.oknion.conceptmap.Model.Ccrelationship;
import com.oknion.conceptmap.Model.CcrelationshipPoints;
import com.oknion.conceptmap.Model.Conceptmap;

@Repository
public class ConceptMapDAOImpl implements ConceptMapDAO {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Conceptmap getConceptMapbyId(int cmId) {
		Session session = this.sessionFactory.getCurrentSession();
		Conceptmap cm = (Conceptmap) session.get(Conceptmap.class, cmId);

		for (Ccrelationship ccrelationship : cm.getCcrelationships()) {
			Double[] points = new Double[8];
			for (CcrelationshipPoints ccrelationshipPoints : ccrelationship
					.getCcrelationshipPointses()) {
				points[ccrelationshipPoints.getOrders()] = new Double(
						ccrelationshipPoints.getPoints());
			}
			ccrelationship.setPoints(points);
		}

		System.out.println("After get Conceptmap:" + cm.getCmName()
				+ ", Transaction is now "
				+ session.getTransaction().getLocalStatus() + "_"
				+ session.getTransaction().isActive());
		return cm;
	}

	@Override
	public void addConceptMap(Conceptmap cm) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(cm);
		System.out.println("After add Conceptmap, Transaction is now "
				+ session.getTransaction().getLocalStatus() + "_"
				+ session.getTransaction().isActive());

	}

	@Override
	public void updateConceptMap(Conceptmap cm) {
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(cm);

	}

	@Override
	public void deleteConceptmap(Conceptmap cm) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(cm);
	}
}
