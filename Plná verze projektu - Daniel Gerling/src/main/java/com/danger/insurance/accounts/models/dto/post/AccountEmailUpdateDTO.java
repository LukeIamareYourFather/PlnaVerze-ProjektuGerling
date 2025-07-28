package com.danger.insurance.accounts.models.dto.post;

import com.danger.insurance.validation.groups.OnUpdateAccount;

import jakarta.validation.constraints.NotBlank;

public class AccountEmailUpdateDTO {

	@NotBlank(message = "Prosím zadejte nový uživatelský E-mail", groups = {OnUpdateAccount.class})
	private String userEmail;

	
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
	
}
