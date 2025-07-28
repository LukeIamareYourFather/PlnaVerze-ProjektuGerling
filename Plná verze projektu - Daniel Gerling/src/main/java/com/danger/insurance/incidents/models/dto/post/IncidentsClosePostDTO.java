package com.danger.insurance.incidents.models.dto.post;

import java.time.LocalDate;

import com.danger.insurance.incidents.data.enums.IncidentResolutionsType;
import com.danger.insurance.validation.groups.OnCloseIncident;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class IncidentsClosePostDTO {

	@NotBlank(message = "Prosím zadejte předmět kroku uzavření pojistné události", groups = {OnCloseIncident.class})
	private String title;
	
	@NotBlank(message = "Prosím zadejte popis kroku uzavření pojistné události", groups = {OnCloseIncident.class})
	private String description;

	private LocalDate commentDate;
	
	@NotNull(message = "Prosím zadejte rozhodnutí kroku zpracování události", groups = {OnCloseIncident.class})
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