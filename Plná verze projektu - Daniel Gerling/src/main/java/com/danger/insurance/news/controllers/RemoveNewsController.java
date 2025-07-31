package com.danger.insurance.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.news.models.service.NewsProcesingServices;

/**
 * Controller responsible for handling the removal of news articles.
 * <p>
 * Mapped under the "/news" base path, this controller provides endpoints for initiating,
 * confirming, and executing the deletion of published news entries. It supports workflows
 * that ensure safe and intentional removal, including confirmation prompts and audit logging.
 * </p>
 */
@PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("news")
public class RemoveNewsController {
	
	@Autowired
	private NewsProcesingServices procesingServices;
	
	/**
	 * Handles the deletion of a specific news article.
	 * <p>
	 * Triggered via GET at "/news/remove/{newsId}", this method removes the news entry
	 * identified by {@code newsId} from the system. It performs the deletion via the service layer
	 * and uses {@code RedirectAttributes} to pass feedback messages to the redirected view.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Invoke service logic to delete the specified news article</li>
	 *   <li>Handle cases where the article does not exist or cannot be deleted</li>
	 *   <li>Pass success or error messages via flash attributes</li>
	 *   <li>Redirect to the news list or confirmation view</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On success → redirect to "/news" with a success message</li>
	 *   <li>On failure → redirect to "/news" with an error message</li>
	 * </ul>
	 *
	 * <h2>Security Considerations:</h2>
	 * <ul>
	 *   <li>Ensure only authorized users can delete news articles</li>
	 *   <li>Log deletion actions for audit and traceability</li>
	 * </ul>
	 *
	 * @param newsId ID of the news article to be deleted
	 * @param redirectAttributes used to pass flash messages across redirects
	 * @return redirect path to the news list or confirmation view
	 */
	@GetMapping("/remove/{newsId}")
	public String handleDeleteNewsFormPost(@PathVariable("newsId") long newsId, RedirectAttributes redirectAttributes) {
	    return procesingServices.processDeleteNewsFormPost(newsId, redirectAttributes);
	}
	
}