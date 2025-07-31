package com.danger.insurance.insurances.controllers;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.services.InsurancesAssigningServices;
import com.danger.insurance.insurances.models.services.InsurancesProcesingServices;
import com.danger.insurance.validation.groups.OnCreateInsurance;

/**
 * Controller responsible for handling insurance contract creation workflows.
 * <p>
 * Mapped under the "/insurances" base path, this controller provides endpoints
 * for rendering the insurance creation form and processing submitted data.
 * It interacts with the service layer to validate input, persist new contracts,
 * and redirect users to appropriate views based on success or failure.
 * </p>
 */
@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
public class CreateInsurancesController {

	@Autowired
	private InsurancesAssigningServices assigningServices;
	
	@Autowired
	private InsurancesProcesingServices procesingServices;
		
	/**
	 * Renders the form view for creating a new insurance contract.
	 * <p>
	 * This method is triggered via a GET request to "/insurances/make" and prepares
	 * the necessary model attributes to populate the form. It initializes an empty
	 * {@code InsuranceDTO} object and loads any required metadata such as tier options,
	 * coverage types, or default values.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Initialize form backing object</li>
	 *   <li>Populate dropdowns or selection fields with metadata</li>
	 *   <li>Forward to the view responsible for rendering the creation form</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code insuranceDTO} – empty DTO for user input binding</li>
	 *   <li>{@code tierOptions} – list of available insurance tiers</li>
	 * </ul>
	 *
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the insurance creation form
	 */
	@GetMapping("make")
	public String renderCreateInsurancesForm(Model model) {
		return assigningServices.addCreateInsurancesFromAttributes(model);
	}
	
	/**
	 * Handles submission of the insurance creation form and performs validation.
	 * <p>
	 * Triggered via POST to "/insurances/make/validate", this method validates the
	 * incoming {@code InsurancesDTO} using the {@code OnCreateInsurance} validation group.
	 * If validation errors are present, it redirects back to the form with error messages.
	 * Otherwise, it proceeds to persist the new insurance contract and redirects to a
	 * confirmation or detail view.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Validate user-submitted insurance data</li>
	 *   <li>Handle binding errors and preserve user input across redirects</li>
	 *   <li>Trigger contract creation via service layer</li>
	 *   <li>Provide feedback using flash attributes</li>
	 * </ul>
	 *
	 * <h2>Validation Group:</h2>
	 * <ul>
	 *   <li>{@code OnCreateInsurance} – ensures required fields are present for creation</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On validation failure → back to "/insurances/make" with errors</li>
	 *   <li>On success → forward to contract detail or confirmation page</li>
	 * </ul>
	 *
	 * @param insuranceDTO DTO containing user-submitted insurance contract data
	 * @param bindingResult result of validation checks
	 * @param redirectAttributes used to pass flash messages and form data across redirects
	 * @return redirect path to either the form (on error) or next step (on success)
	 */
	@PostMapping("make/validate")
	public String handleCreateInsuranceFormPost(@Validated(OnCreateInsurance.class) @ModelAttribute("insurancesDTO") InsurancesDTO insuranceDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return procesingServices.processCreateInsuranceFormPost(insuranceDTO, bindingResult, redirectAttributes);
	}
	
	/**
	 * Finalizes the creation of a new insurance contract.
	 * <p>
	 * This method is triggered via a GET request to "/insurances/make/new" and assumes
	 * that the {@code InsurancesDTO} has already been validated and prepared for persistence.
	 * It delegates the creation logic to the service layer and redirects the user to a
	 * confirmation or detail view upon success.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Receive a populated {@code InsurancesDTO} from the session or previous step</li>
	 *   <li>Invoke service logic to persist the new insurance contract</li>
	 *   <li>Redirect to a success page or contract detail view</li>
	 * </ul>
	 *
	 * <h2>Assumptions:</h2>
	 * <ul>
	 *   <li>DTO has already passed validation (typically via a prior POST step)</li>
	 *   <li>Any required metadata or user context is available</li>
	 * </ul>
	 *
	 * @param insuranceDTO DTO containing finalized insurance contract data
	 * @return name of the view or redirect path after successful creation
	 */
	@GetMapping("make/new") 
	public String handleCreateInsurancesPost(@ModelAttribute("insurancesDTO") InsurancesDTO insuranceDTO) {
		return procesingServices.processCreateInsurancesPost(insuranceDTO);
	}
	
}