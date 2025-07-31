package com.danger.insurance.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.danger.insurance.news.models.service.NewsAssigningServices;
import com.danger.insurance.shared.enums.ActivePageTokens;
import com.danger.insurance.shared.services.CommonSupportServiceShared;
import com.danger.insurance.shared.services.RequiredDataInjecterService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller responsible for displaying published news articles and announcements.
 * <p>
 * Mapped under the "/news" base path, this controller provides endpoints for viewing
 * individual news entries, listing all published articles, and optionally filtering
 * or paginating results. It serves both public-facing and internal audiences depending
 * on the application's configuration.
 * </p>
 */
@Controller
@RequestMapping("news")
public class DisplayNewsController {

	@Autowired
	private RequiredDataInjecterService requiredDataInjecter;
	
	@Autowired
	private CommonSupportServiceShared supportServiceShared;
	
	@Autowired
	private NewsAssigningServices assigningServices;
	
	/**
	 * Renders the view displaying a list of published news articles.
	 * <p>
	 * Triggered via GET at "/news", this method serves as the landing page for the news module.
	 * It retrieves all visible news entries and prepares the model for rendering. It also clears
	 * any session-scoped attributes to reset previous workflows, ensuring a clean state for browsing.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Clear session attributes to reset any prior news creation or editing flows</li>
	 *   <li>Fetch all published news articles from the service or repository layer</li>
	 *   <li>Populate the model with article data for rendering</li>
	 *   <li>Optionally inspect the HTTP request for personalization or analytics</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code newsList} – collection of published news articles</li>
	 *   <li>{@code displayContext} – optional metadata for UI customization</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Acts as the entry point for browsing news content</li>
	 *   <li>May include links to view, search, or create news articles</li>
	 * </ul>
	 *
	 * <h2>Session Management:</h2>
	 * <ul>
	 *   <li>{@code SessionStatus.setComplete()} – clears session-scoped attributes</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass attributes to the view
	 * @param httpRequest the incoming HTTP request, used for context or analytics
	 * @param sessionStatus used to clear session attributes and reset workflow state
	 * @return name of the view template that displays the news list
	 */
	@GetMapping
	public String renderNewsList(Model model, HttpServletRequest httpRequest, SessionStatus sessionStatus) {
		supportServiceShared.removeAllSessionAttributes(ActivePageTokens.NEWS, httpRequest, sessionStatus, model);
		requiredDataInjecter.injectRequiredArticleIfMissing();
		
		return assigningServices.addNewsListAttributes(model);
	}
	
	/**
	 * Renders the detail view for a specific news article.
	 * <p>
	 * Triggered via GET at "/news/{newsId}", this method retrieves the full content
	 * of the news article identified by {@code newsId} and populates the model for display.
	 * It supports both public-facing and internal news entries, depending on the system's configuration.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Fetch the news article from the service or repository layer</li>
	 *   <li>Populate the model with article content and metadata</li>
	 *   <li>Handle missing or invalid article IDs gracefully</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code newsArticle} – full content and metadata of the selected news entry</li>
	 *   <li>{@code displayMode} – optional flag for customizing the view (e.g. public vs. admin)</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed from the news list or search results</li>
	 *   <li>May include links to related articles, comments, or sharing options</li>
	 * </ul>
	 *
	 * <h2>Error Handling:</h2>
	 * <ul>
	 *   <li>If article not found → redirect to error page or show fallback message</li>
	 * </ul>
	 *
	 * @param newsId ID of the news article to display
	 * @param model Spring MVC model used to pass article data to the view
	 * @return name of the view template that displays the news article
	 */
	@GetMapping("{newsId}")
	public String renderNewsArticle(@PathVariable("newsId") long newsId, Model model) {
		return assigningServices.addNewsArticleAttributes(newsId, model);
	}
	
}