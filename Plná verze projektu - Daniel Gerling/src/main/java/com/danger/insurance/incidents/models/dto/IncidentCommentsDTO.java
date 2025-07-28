package com.danger.insurance.incidents.models.dto;

import java.time.LocalDate;

import com.danger.insurance.incidents.data.entities.IncidentsEntity;

public class IncidentCommentsDTO {

	private long incidentCommentId;

	
	private String title;
	
	private String description;

	private LocalDate commentDate;

	private IncidentsEntity incidentsEntity;

	/**
	 * @return the incidentCommentId
	 */
	public long getIncidentCommentId() {
		return incidentCommentId;
	}

	/**
	 * @param incidentCommentId the incidentCommentId to set
	 */
	public void setIncidentCommentId(long incidentCommentId) {
		this.incidentCommentId = incidentCommentId;
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
	 * @return the incidentsEntity
	 */
	public IncidentsEntity getIncidentsEntity() {
		return incidentsEntity;
	}

	/**
	 * @param incidentsEntity the incidentsEntity to set
	 */
	public void setIncidentsEntity(IncidentsEntity incidentsEntity) {
		this.incidentsEntity = incidentsEntity;
	}
	
}