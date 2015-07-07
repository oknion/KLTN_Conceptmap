package com.oknion.conceptmap.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.oknion.conceptmap.Model.Document;

@Repository
public class DocumentDAOImpl implements DocumentDAO {
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Document getDocumentbyId(int documentId) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			Document document = (Document) session.get(Document.class,
					documentId);

			System.out.println("Get Document success");
			System.out.println("Transaction is now "
					+ session.getTransaction().getLocalStatus() + "_"
					+ session.getTransaction().isActive());

			return document;
		} catch (Exception e) {

			return null;

		}
	}

	public void deleteDocumentById(Document document) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(document);
	}

	@Override
	public boolean addDocument(Document document) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(document);
		return false;
	}

	@Override
	public Document getDocByS3KeyId(String s3KeyIdString, String s3BucketId) {
		Session session = this.sessionFactory.getCurrentSession();
		try {
			String hql = "FROM Document Doc WHERE Doc.s3KeyIdString = :s3KeyIdString AND Doc.s3BucketId=:s3BucketId";
			Query query = session.createQuery(hql);
			query.setParameter("s3KeyIdString", s3KeyIdString);
			query.setParameter("s3BucketId", s3BucketId);

			System.out.println(s3KeyIdString + ";" + s3BucketId);

			List<?> results = query.list();
			System.out.println("Get Document success");
			System.out.println("Transaction is now "
					+ session.getTransaction().getLocalStatus() + "_"
					+ session.getTransaction().isActive());
			if (results.size() >= 1) {

				return (Document) results.get(0);
			} else {
				return null;
			}

		} catch (Exception e) {

			return null;

		}
	};

}
