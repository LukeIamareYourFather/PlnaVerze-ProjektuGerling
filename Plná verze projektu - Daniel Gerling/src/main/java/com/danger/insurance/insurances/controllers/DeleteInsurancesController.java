package com.danger.insurance.insurances.controllers;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.archive.models.dto.DeleteInsurancesReasonsDTO;
import com.danger.insurance.insurances.models.services.InsurancesAssigningServices;
import com.danger.insurance.insurances.models.services.InsurancesProcesingServices;
import com.danger.insurance.insurances.models.services.InsurancesVerifyingServices;
import com.danger.insurance.validation.groups.OnDeleteInsurance;

/**
 * Controller responsible for managing the deletion of insurance contracts.
 * <p>
 * Mapped under the relevant insurance routes, this controller handles the user flow
 * for selecting a contract to delete, confirming the deletion reason, and executing
 * the removal. It uses {@code @SessionAttributes} to persist the {@code insurancesReasonsDTO}
 * across multiple requests, allowing for multi-step deletion confirmation.
 * </p>
 */
@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
@SessionAttributes("insurancesReasonsDTO")
public class DeleteInsurancesController {
	
	@Autowired
	private InsurancesAssigningServices assigningServices;
	
	@Autowired
	private InsurancesProcesingServices procesingServices;
	
	@Autowired
	private InsurancesVerifyingServices verifyingServices;
	
	/**
	 * Initializes the {@code DeleteInsurancesReasonsDTO} used to capture the reason
	 * for deleting an insurance contract.
	 * <p>
	 * This method is invoked automatically by Spring when the {@code insurancesReasonsDTO}
	 * attribute is referenced in the controller. It ensures that a fresh instance is
	 * available in the session for multi-step deletion workflows.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Provide a default DTO for capturing deletion reasons</li>
	 *   <li>Support session persistence across multiple requests</li>
	 *   <li>Enable form binding and validation during deletion flow</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Used in views that prompt users to enter a reason for deletion</li>
	 *   <li>Bound to form fields and validated before contract removal</li>
	 * </ul>
	 *
	 * @return a new instance of {@code DeleteInsurancesReasonsDTO}
	 */
	@ModelAttribute("insurancesReasonsDTO")
	public DeleteInsurancesReasonsDTO reasonsDto() {
	    return new DeleteInsurancesReasonsDTO(); 											// Only called once, when session starts
	}
	
	/**
	 * Renders the form view for initiating the deletion of an insurance contract.
	 * <p>
	 * Triggered via a GET request to "/insurances/delete", this method prepares the
	 * model for displaying the deletion reason input form. It ensures that the session-scoped
	 * {@code insurancesReasonsDTO} is available and ready for user input.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Prepare the deletion reason form for user input</li>
	 *   <li>Ensure session attribute {@code insurancesReasonsDTO} is available</li>
	 *   <li>Forward to the view responsible for rendering the deletion interface</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code insurancesReasonsDTO} – bound to form fields for capturing deletion reason</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Typically accessed from a contract detail or management page</li>
	 *   <li>Serves as the first step in a multi-step deletion confirmation flow</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the deletion reason form
	 */
	@GetMapping("delete")
	public String renderDeleteInsurancesForm(Model model) {
		return assigningServices.addDeleteInsurancesFormAttributes(reasonsDto(), model);
	}
	
	/**
	 * Validates the user-submitted reason for deleting an insurance contract.
	 * <p>
	 * Triggered via POST to "/insurances/delete/validate", this method checks the
	 * {@code DeleteInsurancesReasonsDTO} against the {@code OnDeleteInsurance} validation group.
	 * If validation fails, it redirects back to the deletion form with error messages.
	 * If successful, it proceeds to the next step in the deletion workflow.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Validate the deletion reason provided by the user</li>
	 *   <li>Preserve form data and validation errors across redirects</li>
	 *   <li>Redirect to confirmation or execution step upon success</li>
	 * </ul>
	 *
	 * <h2>Validation Group:</h2>
	 * <ul>
	 *   <li>{@code OnDeleteInsurance} – ensures that a valid reason is provided</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On validation failure → back to "/insurances/delete" with errors</li>
	 *   <li>On success → forward to deletion confirmation or execution endpoint</li>
	 * </ul>
	 *
	 * @param insurancesReasonsDTO DTO containing the user's reason for deletion
	 * @param bindingResult result of validation checks
	 * @param redirectAttributes used to pass flash messages and form data across redirects
	 * @return redirect path to either the form (on error) or next step (on success)
	 */
	@PostMapping("delete/validate")
	public String validateDeleteInsuranceFormPost(@Validated(OnDeleteInsurance.class) @ModelAttribute("insurancesReasonsDTO") DeleteInsurancesReasonsDTO insurancesReasonsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return verifyingServices.verifyDeleteInsuranceFormPost(insurancesReasonsDTO, bindingResult, redirectAttributes);
	}
	
	/**
	 * Renders the view for selecting an insurance contract to delete.
	 * <p>
	 * Triggered via GET at "/insurances/delete/find", this method presents the user
	 * with a list of eligible contracts for deletion. It uses the previously captured
	 * {@code DeleteInsurancesReasonsDTO} to filter or annotate the selection process,
	 * ensuring that the deletion reason is carried forward in the workflow.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Display a list of insurance contracts eligible for deletion</li>
	 *   <li>Use the session-scoped {@code insurancesReasonsDTO} to contextualize the view</li>
	 *   <li>Prepare model attributes for rendering selection options</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code insurancesReasonsDTO} – contains the user's reason for deletion</li>
	 *   <li>{@code deletableContracts} – list of contracts that can be deleted</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Follows validation of deletion reason</li>
	 *   <li>Precedes final deletion confirmation or execution</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass attributes to the view
	 * @param insurancesReasonsDTO DTO containing the validated reason for deletion
	 * @return name of the view template that displays deletable insurance contracts
	 */
	@GetMapping("delete/find")
	public String renderFindInsurancesToDelete(Model model, @ModelAttribute("insurancesReasonsDTO") DeleteInsurancesReasonsDTO insurancesReasonsDTO) {
		String referenceLink = "confirmation/insurance-";
		
		return assigningServices.addInsurancesListWithAttributes(referenceLink, model);
	}
	
	/**
	 * Renders the confirmation view for deleting a specific insurance contract.
	 * <p>
	 * Triggered via GET at "/insurances/delete/confirmation/insurance-{insurancesId}", this method
	 * displays a final confirmation page to the user, summarizing the contract to be deleted
	 * and the reason provided. It ensures that the deletion is intentional and informed before
	 * executing any irreversible actions.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Retrieve and display details of the insurance contract targeted for deletion</li>
	 *   <li>Present the user-provided deletion reason from {@code insurancesReasonsDTO}</li>
	 *   <li>Prepare model attributes for rendering the confirmation interface</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code contractDetails} – metadata about the insurance contract to be deleted</li>
	 *   <li>{@code insurancesReasonsDTO} – reason for deletion, previously validated</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Follows contract selection and reason validation steps</li>
	 *   <li>Precedes the actual deletion execution endpoint</li>
	 * </ul>
	 *
	 * <h2>Safety Considerations:</h2>
	 * <ul>
	 *   <li>Ensure user is authorized to delete the specified contract</li>
	 *   <li>Display warnings or audit notes for sensitive deletions</li>
	 * </ul>
	 *
	 * @param insurancesId ID of the insurance contract to be deleted
	 * @param insurancesReasonsDTO DTO containing the validated reason for deletion
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the deletion confirmation page
	 */
	@GetMapping("delete/confirmation/insurance-{insurancesId}")
	public String renderConfirmInsuraceDeletion(@PathVariable("insurancesId") long insurancesId, @ModelAttribute("insurancesReasonsDTO") DeleteInsurancesReasonsDTO insurancesReasonsDTO, Model model) {
		return assigningServices.addConfirmInsuranceDeletionFormAttributes(insurancesReasonsDTO, insurancesId, model);
	}
	
	/**
	 * Executes the deletion of a specific insurance contract after user confirmation.
	 * <p>
	 * Triggered via POST at "/insurances/delete/insurance-{insurancesId}/confirmed", this method
	 * performs the actual removal of the insurance contract identified by {@code insurancesId}.
	 * It uses the previously validated {@code DeleteInsurancesReasonsDTO} to log the reason
	 * and finalize the deletion. Upon completion, it clears the session attributes and redirects
	 * the user to a success or audit page.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Invoke service logic to delete the specified insurance contract</li>
	 *   <li>Log the deletion reason for audit and traceability</li>
	 *   <li>Clear session-scoped attributes using {@code SessionStatus}</li>
	 *   <li>Redirect to a confirmation or summary view with feedback</li>
	 * </ul>
	 *
	 * <h2>Session Management:</h2>
	 * <ul>
	 *   <li>{@code SessionStatus.setComplete()} – clears {@code insurancesReasonsDTO} from session</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On success → redirect to deletion confirmation or audit trail</li>
	 *   <li>On failure → optionally redirect to error page or retry flow</li>
	 * </ul>
	 *
	 * <h2>Security Considerations:</h2>
	 * <ul>
	 *   <li>Ensure user is authorized to delete the specified contract</li>
	 *   <li>Validate contract existence and eligibility for deletion</li>
	 * </ul>
	 *
	 * @param insurancesId ID of the insurance contract to be deleted
	 * @param insurancesReasonsDTO DTO containing the validated reason for deletion
	 * @param sessionStatus used to clear session attributes after completion
	 * @param redirectAttributes used to pass flash messages across redirects
	 * @return redirect path to confirmation or summary view
	 */
	@PostMapping("delete/insurance-{insurancesId}/confirmed")
	public String handleDeleteInsuranceConfirmedPost(@PathVariable("insurancesId") long insurancesId, @ModelAttribute("insurancesReasonsDTO") DeleteInsurancesReasonsDTO insurancesReasonsDTO, SessionStatus sessionStatus, RedirectAttributes redirectAttributes) {
		return procesingServices.processDeleteInsuranceConfirmedPost(insurancesReasonsDTO, insurancesId, redirectAttributes, sessionStatus);
	}
	
}