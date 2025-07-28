package com.danger.insurance.incidents.models.dto.post;

import java.time.LocalDate;

import com.danger.insurance.validation.groups.OnProcessIncident;

import jakarta.validation.constraints.NotBlank;

public class IncidentCommentsCreatePostDTO {

	@NotBlank(message = "Prosím zadejte předmět kroku zpracování události", groups = {OnProcessIncident.class})
	private String title;
	
	@NotBlank(message = "Prosím zadejte popis kroku zpracování pojistné události", groups = {OnProcessIncident.class})
	private String description;

	private LocalDate commentDate;

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

}