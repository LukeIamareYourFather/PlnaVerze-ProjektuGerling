package com.danger.insurance.archive.data.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RemovedIncidentCommentsEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long removedIncidentCommentId;

	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private LocalDate commentDate;
	
	@ManyToOne
	@JoinColumn(name = "deleted_incident_id", nullable = false)
	private RemovedIncidentsEntity removedIncidentEntity;

	/**
	 * @return the removedIncidentCommentId
	 */
	public long getRemovedIncidentCommentId() {
		return removedIncidentCommentId;
	}

	/**
	 * @param removedIncidentCommentId the removedIncidentCommentId to set
	 */
	public void setRemovedIncidentCommentId(long removedIncidentCommentId) {
		this.removedIncidentCommentId = removedIncidentCommentId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the commentDate
	 */
	public LocalDate getCommentDate() {
		return commentDate;
	}

	/**
	 * @param commentDate the commentDate to set
	 */
	public void setCommentDate(LocalDate commentDate) {
		this.commentDate = commentDate;
	}

	/**
	 * @return the removedIncidentEntity
	 */
	public RemovedIncidentsEntity getRemovedIncidentEntity() {
		return removedIncidentEntity;
	}

	/**
	 * @param removedIncidentEntity the removedIncidentEntity to set
	 */
	public void setRemovedIncidentEntity(RemovedIncidentsEntity removedIncidentEntity) {
		this.removedIncidentEntity = removedIncidentEntity;
	}
	
}