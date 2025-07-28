package com.danger.insurance.accounts.models.dto.get;

import com.danger.insurance.accounts.data.enums.AccountRoles;

public class AccountDetailsDTO {

	private String userEmail;

	private AccountRoles userRole;

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	/**
	 * @return the userRole
	 */
	public AccountRoles getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(AccountRoles userRole) {
		this.userRole = userRole;
	}

}
