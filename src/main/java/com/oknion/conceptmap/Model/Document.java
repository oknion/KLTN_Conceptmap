package com.oknion.conceptmap.Model;

// Generated Oct 21, 2014 3:41:47 PM by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Document generated by hbm2java
 */
@Entity
@Table(name = "document")
public class Document implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer documentId;
	private Concept concept;
	private Ccrelationship ccrelationship;
	private Error error;
	private String documentName;

	private int length;
	private byte[] bytes;
	private String name;
	private String type;
	private int documentCcId;
	private String s3KeyIdString;
	private String s3BucketId;

	@Column(name = "s3BucketId", length = 50)
	public String getS3BucketId() {
		return s3BucketId;
	}

	public void setS3BucketId(String s3BucketId) {
		this.s3BucketId = s3BucketId;
	}

	@Column(name = "s3KeyIdString", length = 50)
	public String getS3KeyIdString() {
		return s3KeyIdString;
	}

	public void setS3KeyIdString(String s3KeyIdString) {
		this.s3KeyIdString = s3KeyIdString;
	}

	public int getDocumentCcId() {
		return documentCcId;
	}

	public void setDocumentCcId(int documentCcId) {
		this.documentCcId = documentCcId;
	}

	public Document() {
	}

	public Document(String documentName) {
		this.documentName = documentName;

	}

	public Document(Concept concept, Ccrelationship ccrelationship,
			Error error, String documentName) {
		this.concept = concept;
		this.ccrelationship = ccrelationship;
		this.error = error;
		this.documentName = documentName;

	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "documentId", unique = true)
	public Integer getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	@ManyToOne
	@JoinColumn(name = "ownccId")
	public Concept getConcept() {
		return this.concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}

	@ManyToOne
	@JoinColumn(name = "ccrelaId")
	public Ccrelationship getCcrelationship() {
		return this.ccrelationship;
	}

	public void setCcrelationship(Ccrelationship ccrelationship) {
		this.ccrelationship = ccrelationship;
	}

	@ManyToOne
	@JoinColumn(name = "errorId")
	public Error getError() {
		return this.error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	@Column(name = "documentName", length = 50)
	public String getDocumentName() {
		return this.documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	@Column(name = "docLength")
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Lob
	@Column(name = "docBytes")
	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Column(name = "docName", length = 250)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type", length = 500)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
