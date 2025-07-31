package com.danger.insurance.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.news.models.dto.NewsCreateDTO;
import com.danger.insurance.news.models.service.NewsAssigningServices;
import com.danger.insurance.news.models.service.NewsProcesingServices;
import com.danger.insurance.validation.groups.OnCreateNews;

/**
 * Controller responsible for handling the creation of news articles or announcements.
 * <p>
 * Mapped under the "/news" base path, this controller provides endpoints for rendering
 * the news creation form, validating user input, and submitting new entries to the system.
 * It supports workflows for both internal announcements and public-facing news posts.
 * </p>
 */
@PreAuthorize("hasAnyRole('JOURNALIST', 'ADMINISTRATOR')")
@Controller
@RequestMapping("news")
public class CreateNewsController {
	
	@Autowired
	private NewsAssigningServices assigningServices;
	
	@Autowired
	private NewsProcesingServices procesingServices;
	
	/**
	 * Renders the form view for creating a new news article or announcement.
	 * <p>
	 * Triggered via GET at "/news/create", this method prepares the model with the necessary
	 * attributes to display an empty news creation form. It initializes a fresh {@code NewsDTO}
	 * object for form binding and may include metadata such as category options or default values.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Initialize the form backing object for news creation</li>
	 *   <li>Populate the model with any required metadata (e.g., categories, tags)</li>
	 *   <li>Forward to the view responsible for rendering the news creation interface</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code newsDTO} – empty DTO for user input binding</li>
	 *   <li>{@code categoryOptions} – optional list of available news categories</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by administrators or content managers to publish new updates</li>
	 *   <li>Serves as the first step in the news publishing workflow</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the news creation form
	 */
	@GetMapping("create")
	public String renderCreateNewsForm(Model model) {
		return assigningServices.addCreateNewsFormAttributes(model);
	}
	
	/**
	 * Handles submission of the news creation form and processes the uploaded content.
	 * <p>
	 * Triggered via POST at "/news/create/process", this method validates the user-submitted
	 * {@code NewsCreateDTO} using the {@code OnCreateNews} validation group. It also handles
	 * an optional file upload (e.g., image or attachment) associated with the news article.
	 * If validation fails, it returns to the form view with error messages. If successful,
	 * it persists the news entry and redirects to a confirmation or listing page.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Validate the news article content and metadata</li>
	 *   <li>Process the uploaded file (e.g., save to storage, link to article)</li>
	 *   <li>Handle binding errors and preserve user input across redirects</li>
	 *   <li>Persist the news entry via the service layer</li>
	 *   <li>Provide feedback using flash attributes</li>
	 * </ul>
	 *
	 * <h2>Validation Group:</h2>
	 * <ul>
	 *   <li>{@code OnCreateNews} – ensures required fields are present and valid for creation</li>
	 * </ul>
	 *
	 * <h2>File Handling:</h2>
	 * <ul>
	 *   <li>Supports optional file upload via {@code MultipartFile}</li>
	 *   <li>May include validation for file type, size, or naming</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On validation failure → back to "/news/create" with errors</li>
	 *   <li>On success → redirect to "/news/list" or confirmation view</li>
	 * </ul>
	 *
	 * @param file uploaded file associated with the news article (e.g., image or document)
	 * @param newsCreateDTO DTO containing the news article content and metadata
	 * @param bindingResult result of validation checks
	 * @param model Spring MVC model used to pass attributes to the view
	 * @param redirectAttributes used to pass flash messages across redirects
	 * @return redirect path to either the form (on error) or listing/confirmation view (on success)
	 */
	@PostMapping("create/process")
	public String handleCreateNewsFormPost(@RequestParam("file") MultipartFile file,@Validated(OnCreateNews.class) @ModelAttribute("newsCreateDTO") NewsCreateDTO newsCreateDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		return procesingServices.processCreateNewsFormPost(file, newsCreateDTO, bindingResult, redirectAttributes, model);
	}
	
}