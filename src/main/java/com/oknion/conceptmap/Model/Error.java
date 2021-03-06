package com.oknion.conceptmap.Model;

// Generated Oct 21, 2014 3:41:47 PM by Hibernate Tools 4.0.0

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Error generated by hbm2java
 */
@Entity
@Table(name = "error")
public class Error implements java.io.Serializable {

	private Integer errId;
	private Conceptmap conceptmap;
	private String descrip;
	private String name;
	private Set<Document> documents = new HashSet<Document>(0);

	public Error() {
	}

	public Error(Conceptmap conceptmap, String descrip, String name) {
		this.conceptmap = conceptmap;
		this.descrip = descrip;
		this.name = name;
	}

	public Error(Conceptmap conceptmap, String descrip, String name,
			Set<Document> documents) {
		this.conceptmap = conceptmap;
		this.descrip = descrip;
		this.name = name;
		this.documents = documents;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "errId", unique = true, nullable = false)
	public Integer getErrId() {
		return this.errId;
	}

	public void setErrId(Integer errId) {
		this.errId = errId;
	}

	@ManyToOne
	@JoinColumn(name = "ofcmId", nullable = false)
	public Conceptmap getConceptmap() {
		return this.conceptmap;
	}

	public void setConceptmap(Conceptmap conceptmap) {
		this.conceptmap = conceptmap;
	}

	@Column(name = "descrip", nullable = false)
	public String getDescrip() {
		return this.descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "error")
	@Cascade({ CascadeType.ALL })
	public Set<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

}
