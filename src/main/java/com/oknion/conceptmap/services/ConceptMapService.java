package com.oknion.conceptmap.services;

import java.util.Set;

import com.oknion.conceptmap.Model.Conceptmap;
import com.oknion.conceptmap.Model.Document;

public interface ConceptMapService {

	public void addConceptMap(Conceptmap cm);

	public Conceptmap getConceptMapbyId(int cmId);

	public void updateConceptmap(Conceptmap cm);

	public void deleteConceptmap(Conceptmap cm);

	public Set<Document> getDocumentByCM(int cmId);
}
