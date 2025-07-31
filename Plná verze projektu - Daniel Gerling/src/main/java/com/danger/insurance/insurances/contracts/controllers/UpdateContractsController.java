package com.danger.insurance.insurances.contracts.controllers;

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

import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsAssigningServices;
import com.danger.insurance.insurances.contracts.models.services.support.ContractsProcesingServices;
import com.danger.insurance.validation.groups.OnUpdateContract;

/**
 * Controller responsible for updating details of existing insurance contracts.
 * Mapped under "/insurances", the UpdateContractsController facilitates workflows to
 * modify contract attributes such as coverage tier, expiry date, assigned insured parties,
 * or administrative notes. This class may support single-step and multi-step updates
 * depending on validation requirements.
 */
@PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("insurances")
public class UpdateContractsController {

	@Autowired
	private ContractsAssigningServices assigningServices;
	
	@Autowired
	private ContractsProcesingServices procesingServices;
	
	/**
	 * Renders the edit form for updating an existing insurance contract.
	 * Accessed via GET at "/insurances/contract-{contractId}/edit", this method retrieves
	 * the current contract details and populates the form view to allow authorized users
	 * to modify attributes such as coverage tier, expiration date, or administrative notes.
	 *
	 * Key Features to Support:
	 * - Inline display of current contract values
	 * - Editable fields with validation hints (e.g., date pickers, dropdowns)
	 * - Optional warnings for sensitive changes (e.g., tier downgrade, early termination)
	 * - Audit-friendly structure for tracking changes
	 *
	 * @param contractId ID of the contract to be edited
	 * @param model Spring MVC model used to pass contract data to the view
	 * @return name of the view that displays the contract edit form
	 */
	@GetMapping("contract-{contractId}/edit")
	public String renderEditContractForm(@PathVariable("contractId") long contractId, Model model) {
		return assigningServices.addEditContractFormAttributes(contractId, model);
	}
	
	/**
	 * Handles submission of the contract edit form.
	 * Validates the input, updates the contract if valid, and redirects to a confirmation page.
	 *
	 * @param contractId ID of the contract being edited
	 * @param contractsDTO DTO containing updated contract data
	 * @param bindingResult result of validation checks
	 * @param redirectAttributes attributes for flash messages during redirect
	 * @return redirect path to confirmation or back to form on error
	 */
	@PostMapping("contract-{contractId}/edit/confirm")
	public String handleEditContractFormSubmit(@PathVariable("contractId") long contractId,@Validated(OnUpdateContract.class) @ModelAttribute("contractDTO") ContractsDTO contractsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		return procesingServices.processEditContractFormSubmit(contractsDTO, contractId, bindingResult, redirectAttributes);
	}
	
}