package com.danger.insurance.incidents.data.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class IncidentCommentsEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long incidentCommentId;

	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private LocalDate commentDate;
	
	@ManyToOne
	@JoinColumn(name = "incident_id", nullable = false)
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