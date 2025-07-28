package com.danger.insurance.archive.models.dto;

import com.danger.insurance.incidents.data.enums.IncidentRemovalReasons;
import com.danger.insurance.validation.groups.OnRemoveIncident;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RemoveIncidentReasonsDTO {

	@NotNull(message = "Prosím zadejte důvod odstranění pojistné události", groups = {OnRemoveIncident.class})
	private IncidentRemovalReasons incidentRemovalReason;

	@NotBlank(message = "Prosím zadejte popis důvodu odstranění události", groups = {OnRemoveIncident.class})
	private String removalDescription;

	/**
	 * @return the incidentRemovalReason
	 */
	public IncidentRemovalReasons getIncidentRemovalReason() {
		return incidentRemovalReason;
	}

	/**
	 * @param incidentRemovalReason the incidentRemovalReason to set
	 */
	public void setIncidentRemovalReason(IncidentRemovalReasons incidentRemovalReason) {
		this.incidentRemovalReason = incidentRemovalReason;
	}

	/**
	 * @return the removalDescription
	 */
	public String getRemovalDescription() {
		return removalDescription;
	}

	/**
	 * @param removalDescription the removalDescription to set
	 */
	public void setRemovalDescription(String removalDescription) {
		this.removalDescription = removalDescription;
	}
	
	
}