package com.oknion.conceptmap.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oknion.conceptmap.DAO.ConceptMapDAO;
import com.oknion.conceptmap.Model.Concept;
import com.oknion.conceptmap.Model.Conceptmap;
import com.oknion.conceptmap.Model.Document;

@Service
@Transactional
public class ConceptMapServiceImpl implements ConceptMapService {

	private ConceptMapDAO conceptMapDao;

	public void setConceptMapDao(ConceptMapDAO conceptMapDao) {
		this.conceptMapDao = conceptMapDao;
	}

	@Override
	public void addConceptMap(Conceptmap cm) {
		conceptMapDao.addConceptMap(cm);

	}

	@Override
	public Conceptmap getConceptMapbyId(int cmId) {

		return conceptMapDao.getConceptMapbyId(cmId);
	}

	@Override
	public void updateConceptmap(Conceptmap cm) {

		conceptMapDao.updateConceptMap(cm);
	}

	@Override
	public void deleteConceptmap(Conceptmap cm) {
		conceptMapDao.deleteConceptmap(cm);
	}

	@Override
	public Set<Document> getDocumentByCM(int cmId) {
		Conceptmap cm = conceptMapDao.getConceptMapbyId(cmId);
		Set<Document> documents = new HashSet<Document>();
		for (Concept concept : cm.getConcepts()) {
			for (Document document : concept.getDocuments()) {
				documents.add(document);
			}
		}
		return documents;
	}

}
