package com.oknion.conceptmap.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oknion.conceptmap.DAO.ConceptMapDAO;
import com.oknion.conceptmap.DAO.DocumentDAO;
import com.oknion.conceptmap.Model.Concept;
import com.oknion.conceptmap.Model.Conceptmap;
import com.oknion.conceptmap.Model.Document;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

	private DocumentDAO documentDao;
	private ConceptMapDAO conceptMapDao;

	public void setConceptMapDao(ConceptMapDAO conceptMapDao) {
		this.conceptMapDao = conceptMapDao;
	}

	public void setDocumentDao(DocumentDAO documentDao) {
		this.documentDao = documentDao;
	}

	@Override
	public Document getDocumentbyId(int documentId) {
		// TODO Auto-generated method stub
		return documentDao.getDocumentbyId(documentId);
	}

	@Override
	public Document getDocumentByConcept(int cmId, int ccId) {
		Conceptmap conceptmap = conceptMapDao.getConceptMapbyId(cmId);
		System.out.print("Check id concept and get documnet");
		Document document = null;
		for (Concept concept : conceptmap.getConcepts()) {
			System.out.print((double) concept.getKeyId() + "_" + (double) ccId
					+ "_" + ((double) concept.getKeyId() == (double) ccId));
			if ((double) concept.getKeyId() == (double) ccId) {
				System.out.print("One document detected!");
				if (concept.getDocuments().size() >= 1) {
					for (Document doc : concept.getDocuments()) {
						document = doc;
					}
				}
			}

		}
		return document;
	}

	@Override
	public void deleteDocumentById(int documentId) {
		Document document = documentDao.getDocumentbyId(documentId);
		if (document != null) {
			documentDao.deleteDocumentById(document);
		}

	}

	@Override
	public boolean addDocument(Document document) {
		this.documentDao.addDocument(document);
		return false;
	}

	@Override
	public Document getDocByS3KeyId(String s3KeyId, String s3BucketId) {
		return this.documentDao.getDocByS3KeyId(s3KeyId, s3BucketId);
	}
}
