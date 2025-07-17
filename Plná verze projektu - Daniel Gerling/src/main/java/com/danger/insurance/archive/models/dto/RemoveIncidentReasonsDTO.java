package com.danger.insurance.archive.models.dto;

import com.danger.insurance.incidents.data.enums.IncidentRemovalReasons;

public class RemoveIncidentReasonsDTO {

	private IncidentRemovalReasons incidentRemovalReason;

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