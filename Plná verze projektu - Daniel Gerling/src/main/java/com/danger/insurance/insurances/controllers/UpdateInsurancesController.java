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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.insurances.models.services.InsurancesAssigningServices;
import com.danger.insurance.insurances.models.services.InsurancesProcesingServices;
import com.danger.insurance.validation.groups.OnUpdateInsurance;

/**
 * Controller responsible for handling updates to existing insurance contracts.
 * <p>
 * Mapped under the "/insurances" base path, this controller provides endpoints
 * for rendering edit forms, validating user input, and applying updates to insurance
 * contract records. It supports multi-step workflows, session management, and
 * validation groups to ensure safe and auditable modifications.
 * </p>
 */
@PreAuthorize("hasAnyRole('ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
public class UpdateInsurancesController {
	
	@Autowired
	private InsurancesAssigningServices assigningServices;
	
	@Autowired
	private InsurancesProcesingServices procesingServices;
	
	/**
	 * Renders the form view for editing an existing insurance contract.
	 * <p>
	 * Triggered via GET at "/insurances/insurance-{insuranceId}/edit", this method retrieves
	 * the current details of the insurance contract identified by {@code insuranceId} and
	 * prepares the model for rendering the edit form. It enables authorized users to update
	 * contract attributes such as coverage tier, expiration date, or administrative notes.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Fetch the current insurance contract data from the service layer</li>
	 *   <li>Populate the model with contract details and editable options</li>
	 *   <li>Forward to the view responsible for rendering the edit form</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code insuranceDTO} – pre-filled DTO for form binding</li>
	 *   <li>{@code tierOptions} – list of available coverage tiers</li>
	 *   <li>{@code statusOptions} – list of valid contract statuses</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed from contract detail or management views</li>
	 *   <li>Serves as the first step in a multi-step update workflow</li>
	 * </ul>
	 *
	 * <h2>Error Handling:</h2>
	 * <ul>
	 *   <li>If contract not found → redirect to error page or show fallback message</li>
	 * </ul>
	 *
	 * @param insurancesId ID of the insurance contract to be edited
	 * @param model Spring MVC model used to pass contract data to the view
	 * @return name of the view template that displays the insurance edit form
	 */
	@GetMapping("insurance-{insuranceId}/edit")
	public String renderEditInsuranceForm(@PathVariable("insuranceId") long insurancesId, Model model) {
		return assigningServices.addEditInsuranceFormAttributes(insurancesId, model);
	}
	
	/**
	 * Handles submission of the insurance contract edit form and applies updates.
	 * <p>
	 * Triggered via POST at "/insurances/insurance-{insuranceId}/edit/confirmed", this method
	 * validates the user-submitted {@code InsurancesDTO} using the {@code OnUpdateInsurance}
	 * validation group. If validation fails, it redirects back to the edit form with error messages.
	 * If successful, it updates the contract and redirects to the detail view with a success message.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Validate the updated insurance contract data</li>
	 *   <li>Handle binding errors and preserve user input across redirects</li>
	 *   <li>Apply updates via the service layer</li>
	 *   <li>Provide feedback using flash attributes</li>
	 * </ul>
	 *
	 * <h2>Validation Group:</h2>
	 * <ul>
	 *   <li>{@code OnUpdateInsurance} – ensures required fields are valid for update operations</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On validation failure → back to "/insurances/insurance-{insuranceId}/edit" with errors</li>
	 *   <li>On success → redirect to "/insurances/insurance-{insuranceId}" with confirmation</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Follows the edit form rendering step</li>
	 *   <li>Completes the update workflow for insurance contracts</li>
	 * </ul>
	 *
	 * <h2>Security Considerations:</h2>
	 * <ul>
	 *   <li>Ensure user is authorized to modify the specified contract</li>
	 *   <li>Log changes for audit and traceability</li>
	 * </ul>
	 *
	 * @param insurancesId ID of the insurance contract being updated
	 * @param insurancesDTO DTO containing updated contract data
	 * @param bindingResult result of validation checks
	 * @param redirectAttributes used to pass flash messages across redirects
	 * @return redirect path to either the edit form (on error) or contract detail view (on success)
	 */
	@PostMapping("insurance-{insuranceId}/edit/confirmed")
	public String handleEditInsuranceFormPost(@PathVariable("insuranceId") long insurancesId, @Validated(OnUpdateInsurance.class) @ModelAttribute("insurancesDTO") InsurancesDTO insurancesDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return procesingServices.processEditInsuranceFormPost(insurancesDTO, insurancesId, bindingResult, redirectAttributes);
	}
	
}