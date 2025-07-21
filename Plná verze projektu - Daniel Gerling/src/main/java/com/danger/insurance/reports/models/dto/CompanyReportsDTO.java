package com.danger.insurance.reports.models.dto;

import java.math.BigDecimal;

public class CompanyReportsDTO {

	private long policyOwnerCount;
	private long insuredCount;
	private long personCount;
	private long activeContractsCount;
	private BigDecimal contractsValue;
	private long incidentsCount;
	private long processingIncidentsCount;
	private long processedIncidentsCount;
	/**
	 * @return the policyOwnerCount
	 */
	public long getPolicyOwnerCount() {
		return policyOwnerCount;
	}
	/**
	 * @param policyOwnerCount the policyOwnerCount to set
	 */
	public void setPolicyOwnerCount(long policyOwnerCount) {
		this.policyOwnerCount = policyOwnerCount;
	}
	/**
	 * @return the insuredCount
	 */
	public long getInsuredCount() {
		return insuredCount;
	}
	/**
	 * @param insuredCount the insuredCount to set
	 */
	public void setInsuredCount(long insuredCount) {
		this.insuredCount = insuredCount;
	}
	/**
	 * @return the personCount
	 */
	public long getPersonCount() {
		return personCount;
	}
	/**
	 * @param personCount the personCount to set
	 */
	public void setPersonCount(long personCount) {
		this.personCount = personCount;
	}
	/**
	 * @return the activeContractsCount
	 */
	public long getActiveContractsCount() {
		return activeContractsCount;
	}
	/**
	 * @param activeContractsCount the activeContractsCount to set
	 */
	public void setActiveContractsCount(long activeContractsCount) {
		this.activeContractsCount = activeContractsCount;
	}
	/**
	 * @return the contractsValue
	 */
	public BigDecimal getContractsValue() {
		return contractsValue;
	}
	/**
	 * @param contractsValue the contractsValue to set
	 */
	public void setContractsValue(BigDecimal contractsValue) {
		this.contractsValue = contractsValue;
	}
	/**
	 * @return the incidentsCount
	 */
	public long getIncidentsCount() {
		return incidentsCount;
	}
	/**
	 * @param incidentsCount the incidentsCount to set
	 */
	public void setIncidentsCount(long incidentsCount) {
		this.incidentsCount = incidentsCount;
	}
	/**
	 * @return the processingIncidentsCount
	 */
	public long getProcessingIncidentsCount() {
		return processingIncidentsCount;
	}
	/**
	 * @param processingIncidentsCount the processingIncidentsCount to set
	 */
	public void setProcessingIncidentsCount(long processingIncidentsCount) {
		this.processingIncidentsCount = processingIncidentsCount;
	}
	/**
	 * @return the processedIncidentsCount
	 */
	public long getProcessedIncidentsCount() {
		return processedIncidentsCount;
	}
	/**
	 * @param processedIncidentsCount the processedIncidentsCount to set
	 */
	public void setProcessedIncidentsCount(long processedIncidentsCount) {
		this.processedIncidentsCount = processedIncidentsCount;
	}
	
	
}