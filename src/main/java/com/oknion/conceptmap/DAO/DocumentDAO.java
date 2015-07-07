package com.oknion.conceptmap.DAO;

import com.oknion.conceptmap.Model.Document;

public interface DocumentDAO {

	public Document getDocumentbyId(int documentId);

	public void deleteDocumentById(Document document);

	public boolean addDocument(Document document);

	public Document getDocByS3KeyId(String s3KeyId, String s3BucketId);
}
