package com.oknion.conceptmap.DAO;

import com.oknion.conceptmap.Model.Conceptmap;

public interface ConceptMapDAO {

	public void addConceptMap(Conceptmap cm);

	public Conceptmap getConceptMapbyId(int cmId);

	public void updateConceptMap(Conceptmap cm);

	public void deleteConceptmap(Conceptmap cm);
}
