package com.danger.insurance.archive.models.dto;

import java.time.LocalDate;

import com.danger.insurance.archive.data.entities.RemovedIncidentsEntity;

public class RemovedIncidentCommentsDTO {

	private long removedIncidentCommentId;

	private String title;
	private String description;
	
	private LocalDate commentDate;
	
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