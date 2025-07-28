package com.danger.insurance.news.models.dto;

import com.danger.insurance.validation.groups.OnCreateNews;
import com.danger.insurance.validation.groups.OnUpdateNews;

import jakarta.validation.constraints.NotEmpty;

public class NewsCreateDTO {

	@NotEmpty(message = "Prosím zadejte předmět novinového článku", groups = {OnCreateNews.class, OnUpdateNews.class})
	private String title;

	@NotEmpty(message = "Prosím zadejte popis novinového článku", groups = {OnCreateNews.class, OnUpdateNews.class})
	private String description;
	
	@NotEmpty(message = "Prosím zadejte obsah novinového článku", groups = {OnCreateNews.class, OnUpdateNews.class})
	private String content;

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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}