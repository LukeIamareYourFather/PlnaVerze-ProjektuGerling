package com.danger.insurance.news.data.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class NewsEntity {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long newsId;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private String pictureUrl;
	
	@Column(nullable = false)
	private LocalDate postDate;

	/**
	 * @return the newsId
	 */
	public long getNewsId() {
		return newsId;
	}

	/**
	 * @param newsId the newsId to set
	 */
	public void setNewsId(long newsId) {
		this.newsId = newsId;
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
	
	/**
	 * @return the pictureUrl
	 */
	public String getPictureUrl() {
		return pictureUrl;
	}

	/**
	 * @param pictureUrl the pictureUrl to set
	 */
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	/**
	 * @return the postDate
	 */
	public LocalDate getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate the postDate to set
	 */
	public void setPostDate(LocalDate postDate) {
		this.postDate = postDate;
	}

	
	
}