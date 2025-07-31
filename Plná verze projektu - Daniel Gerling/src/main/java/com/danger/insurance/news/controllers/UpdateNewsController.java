package com.danger.insurance.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.news.models.dto.NewsCreateDTO;
import com.danger.insurance.news.models.service.NewsAssigningServices;
import com.danger.insurance.news.models.service.NewsProcesingServices;
import com.danger.insurance.news.models.service.NewsVerifyingServices;
import com.danger.insurance.validation.groups.OnUpdateNews;

/**
 * Controller responsible for handling updates to published news articles.
 * <p>
 * Mapped under the "/news" base path, this controller provides endpoints for rendering
 * edit forms, validating user-submitted changes, and applying updates to existing news entries.
 * It supports workflows for both internal announcements and public-facing articles, ensuring
 * safe and traceable modifications.
 * </p>
 */
@PreAuthorize("hasAnyRole('JOURNALIST', 'MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("news")
public class UpdateNewsController {

	@Autowired
	private NewsAssigningServices assigningServices;
	
	@Autowired
	private NewsProcesingServices procesingServices;
	
	@Autowired
	private NewsVerifyingServices verifyingServices;
	
	/**
	 * Renders the form view for editing an existing news article.
	 * <p>
	 * Triggered via GET at "/news/{newsId}/edit", this method retrieves the current content
	 * of the news article identified by {@code newsId} and prepares the model for rendering
	 * the edit interface. It enables authorized users to revise the article's title, body,
	 * category, or associated media.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Fetch the existing news article from the service or repository layer</li>
	 *   <li>Populate the model with article data for form binding</li>
	 *   <li>Provide auxiliary data such as category options or formatting hints</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code newsUpdateDTO} – pre-filled DTO for form binding</li>
	 *   <li>{@code categoryOptions} – list of available categories for selection</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by editors or administrators from the article detail or list view</li>
	 *   <li>Serves as the first step in the news update workflow</li>
	 * </ul>
	 *
	 * <h2>Error Handling:</h2>
	 * <ul>
	 *   <li>If article not found → redirect to error page or show fallback message</li>
	 * </ul>
	 *
	 * @param newsId ID of the news article to be edited
	 * @param model Spring MVC model used to pass article data to the view
	 * @return name of the view template that displays the news edit form
	 */
	@GetMapping("{newsId}/edit")
	public String renderUpdateNewsForm(@PathVariable("newsId") long newsId, Model model) {
		return assigningServices.addUpdateNewsFormAttributes(newsId, model);
	}
	
	/**
	 * Validates and processes the update form submission for a specific news article.
	 * <p>
	 * Triggered via POST at "/news/{newsId}/edit/validate", this method checks the
	 * {@code NewsCreateDTO} against the {@code OnUpdateNews} validation group. If validation
	 * fails, it redirects back to the edit form with error messages. If successful, it applies
	 * the updates and redirects to the article detail view or confirmation page.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Validate the updated news content and metadata</li>
	 *   <li>Handle binding errors and preserve user input across redirects</li>
	 *   <li>Apply changes via the service layer</li>
	 *   <li>Provide feedback using flash attributes</li>
	 * </ul>
	 *
	 * <h2>Validation Group:</h2>
	 * <ul>
	 *   <li>{@code OnUpdateNews} – ensures required fields are valid for update operations</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On validation failure → back to "/news/{newsId}/edit" with errors</li>
	 *   <li>On success → redirect to "/news/{newsId}" with confirmation</li>
	 * </ul>
	 *
	 * <h2>Security Considerations:</h2>
	 * <ul>
	 *   <li>Ensure user is authorized to modify the specified article</li>
	 *   <li>Log changes for audit and traceability</li>
	 * </ul>
	 *
	 * @param newsId ID of the news article being updated
	 * @param newsCreateDTO DTO containing updated article data
	 * @param bindingResult result of validation checks
	 * @param redirectAttributes used to pass flash messages across redirects
	 * @return redirect path to either the edit form (on error) or article detail view (on success)
	 */
	@PostMapping("{newsId}/edit/validate")
	public String validateUpdateNewsFormPost(@PathVariable("newsId") long newsId, @Validated(OnUpdateNews.class) @ModelAttribute("newsCreateDTO") NewsCreateDTO newsCreateDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return verifyingServices.verifyUpdateNewsFormPost(newsCreateDTO, newsId, bindingResult, redirectAttributes);
	}
	
	/**
	 * Finalizes the update of a specific news article after validation.
	 * <p>
	 * Triggered via GET at "/news/{newsId}/edit/confirmed", this method applies the
	 * changes submitted through the update form and persists them via the service layer.
	 * It assumes that validation has already been performed in a prior step. Upon successful
	 * update, it redirects to the article detail view with a confirmation message.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Apply the updated news content and metadata to the existing article</li>
	 *   <li>Persist changes via the service or repository layer</li>
	 *   <li>Pass success feedback using flash attributes</li>
	 *   <li>Redirect to the updated article's detail view</li>
	 * </ul>
	 *
	 * <h2>Assumptions:</h2>
	 * <ul>
	 *   <li>Validation has already occurred in a previous POST step</li>
	 *   <li>{@code newsCreateDTO} contains clean and verified data</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On success → redirect to "/news/{newsId}" with confirmation</li>
	 *   <li>On failure → optionally redirect to error page or retry flow</li>
	 * </ul>
	 *
	 * <h2>Security Considerations:</h2>
	 * <ul>
	 *   <li>Ensure user is authorized to modify the specified article</li>
	 *   <li>Log update actions for audit and traceability</li>
	 * </ul>
	 *
	 * @param newsId ID of the news article being updated
	 * @param newsCreateDTO DTO containing the finalized article data
	 * @param redirectAttributes used to pass flash messages across redirects
	 * @return redirect path to the updated article's detail view
	 */
	@GetMapping("{newsId}/edit/confirmed")
	public String handleUpdateNewsFormPost(@PathVariable("newsId") long newsId, @ModelAttribute("newsCreateDTO") NewsCreateDTO newsCreateDTO, RedirectAttributes redirectAttributes) {
		return procesingServices.processUpdateNewsFormPost(newsId, newsCreateDTO, redirectAttributes);
	}
	
}