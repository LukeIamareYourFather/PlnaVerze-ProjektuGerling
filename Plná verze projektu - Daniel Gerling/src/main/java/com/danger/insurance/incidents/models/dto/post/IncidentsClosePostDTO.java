package com.danger.insurance.incidents.models.dto.post;

import java.time.LocalDate;

import com.danger.insurance.incidents.data.enums.IncidentResolutionsType;

public class IncidentsClosePostDTO {

	private String title;
	private String description;

	private LocalDate commentDate;
	
	private IncidentResolutionsType incidentResolution;

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
	 * @return the incidentResolution
	 */
	public IncidentResolutionsType getIncidentResolution() {
		return incidentResolution;
	}

	/**
	 * @param incidentResolution the incidentResolution to set
	 */
	public void setIncidentResolution(IncidentResolutionsType incidentResolution) {
		this.incidentResolution = incidentResolution;
	}

}