package com.oknion.conceptmap.services;

import com.oknion.conceptmap.Model.Document;

public interface DocumentService {

	public Document getDocumentbyId(int documentId);

	public Document getDocumentByConcept(int cmId, int ccId);

	public void deleteDocumentById(int documentId);

	public boolean addDocument(Document document);

	public Document getDocByS3KeyId(String s3KeyId, String s3BucketId);
}
