package com.danger.insurance.parties.controllers;

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

import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;
import com.danger.insurance.parties.models.service.PartiesAssigningServices;
import com.danger.insurance.parties.models.service.PartiesProcesingServices;
import com.danger.insurance.validation.groups.OnUpdateParty;

/**
 * Controller responsible for handling updates to party entities.
 * <p>
 * Mapped under the "/parties" base path, this controller provides endpoints for
 * rendering update forms, validating submitted changes, and applying modifications
 * to existing party records such as policy owners, clients, or vendors.
 * </p>
 */
@PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATOR')")
@Controller
@RequestMapping("parties")
public class UpdatePartiesController {
	
	@Autowired
	private PartiesAssigningServices assigningServices;
	
	@Autowired
	private PartiesProcesingServices procesingServices;
	
	/**
	 * Renders the update form for a specific party profile.
	 * <p>
	 * Triggered via GET at "/parties/profile-{partyId}/update", this method retrieves the
	 * party entity identified by {@code partyId} and prepares the model with its current
	 * data for editing. It supports workflows for modifying party details such as contact
	 * information, status, or classification.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Fetch the existing party record from the service or repository layer</li>
	 *   <li>Populate the model with pre-filled data for form binding</li>
	 *   <li>Forward to the view responsible for rendering the update interface</li>
	 * </ul>
	 *
	 * <h2>Model Attributes:</h2>
	 * <ul>
	 *   <li>{@code partyUpdateDTO} – DTO containing current party data for editing</li>
	 *   <li>{@code partyMetadata} – optional metadata such as party type, region, or status</li>
	 * </ul>
	 *
	 * <h2>Usage Context:</h2>
	 * <ul>
	 *   <li>Accessed by administrators or authorized users to modify party records</li>
	 *   <li>Supports workflows for correcting data, updating contact info, or changing classification</li>
	 * </ul>
	 *
	 * <h2>Error Handling:</h2>
	 * <ul>
	 *   <li>If party not found → redirect to "/parties/not-found" or show fallback message</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Include audit trail preview or last modified info</li>
	 *   <li>Support conditional fields based on party type</li>
	 *   <li>Enable inline validation or dynamic field rendering</li>
	 * </ul>
	 *
	 * @param partyId ID of the party entity to be updated
	 * @param model Spring MVC model used to pass attributes to the view
	 * @return name of the view template that displays the update form
	 */
	@GetMapping("profile-{partyId}/update")
	public String renderUpdatePartyForm(@PathVariable("partyId") long partyId, Model model) {
		return assigningServices.addUpdatePartyFormAttributes(partyId, model);
	}
	
	/**
	 * Validates and applies updates to a specific party profile.
	 * <p>
	 * Triggered via POST at "/parties/profile-{partyId}/update/confirmed", this method receives
	 * a populated {@code PartiesDetailsDTO} containing the updated party data. It validates the
	 * input using the {@code OnUpdateParty} group, and if successful, persists the changes via
	 * the service layer. Feedback is passed using {@code RedirectAttributes}, and the user is
	 * redirected to a confirmation or profile view.
	 * </p>
	 *
	 * <h2>Responsibilities:</h2>
	 * <ul>
	 *   <li>Validate the submitted update data</li>
	 *   <li>Handle binding errors and return to the update form if needed</li>
	 *   <li>Apply changes to the party entity via the service layer</li>
	 *   <li>Pass success or error messages via flash attributes</li>
	 * </ul>
	 *
	 * <h2>Validation Group:</h2>
	 * <ul>
	 *   <li>{@code OnUpdateParty} – ensures required fields are present and valid for updates</li>
	 * </ul>
	 *
	 * <h2>Redirect Behavior:</h2>
	 * <ul>
	 *   <li>On validation failure → return to "/parties/profile-{partyId}/update" with error messages</li>
	 *   <li>On success → redirect to "/parties/profile-{partyId}" with confirmation</li>
	 * </ul>
	 *
	 * <h2>Security Considerations:</h2>
	 * <ul>
	 *   <li>Ensure user is authorized to modify the specified party</li>
	 *   <li>Log update actions for audit and traceability</li>
	 * </ul>
	 *
	 * <h2>Extension Ideas:</h2>
	 * <ul>
	 *   <li>Support partial updates or field-level change tracking</li>
	 *   <li>Include audit trail preview or version history</li>
	 *   <li>Trigger notifications or downstream updates if linked entities are affected</li>
	 * </ul>
	 *
	 * @param partiesDetailsDTO DTO containing updated party data
	 * @param bindingResult result of validation checks
	 * @param model Spring MVC model used to pass attributes to the view
	 * @param partyId ID of the party entity being updated
	 * @param redirectAttributes used to pass feedback messages across redirects
	 * @return redirect path to the update form (on error) or profile view (on success)
	 */
	@PostMapping("profile-{partyId}/update/confirmed")
	public String handleUpdatePartyFormPost(@Validated(OnUpdateParty.class) @ModelAttribute("formDTO") PartiesDetailsDTO partiesDetailsDTO, BindingResult bindingResult, Model model, @PathVariable("partyId") long partyId, RedirectAttributes redirectAttributes) {
		PartiesCreateDTO wrongDTO = null;
		String successRedirect = "parties/profile-";
		String failRedirect = "parties/profile-"  + partyId + "/update";
		
		return procesingServices.processPartyValuesFormSubmit(bindingResult, redirectAttributes, wrongDTO, partiesDetailsDTO, successRedirect, failRedirect);
	}
	
}