package org.java.restfulapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;

import org.java.restfulapi.utils.Status;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Table;

@Entity
@Table
public class Work {

	private static final long serialVersionUID = 1L;
	
	public Work() {
	}

	public Work(long id, String workName, Date startingDate, Date endingDate) {
		super();
		this.id = id;
		this.workName = workName;
		this.startingDate = startingDate;
		this.endingDate = endingDate;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "workName")
	private String workName;
	
	@Column(name = "startingDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	private Date  startingDate;
	
	@Column(name = "endingDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
	private Date  endingDate;
	
	@Column(name = "status")
	private Status status;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public Date getStartingDate() {
		return startingDate;
	}
	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}
	public Date getEndingDate() {
		return endingDate;
	}
	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
