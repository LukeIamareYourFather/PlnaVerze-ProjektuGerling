package com.danger.insurance.accounts.models.dto.post;

import com.danger.insurance.validation.groups.OnUpdateAccount;

import jakarta.validation.constraints.NotBlank;

public class AccountPasswordUpdateDTO {

	@NotBlank(message = "Prosím zadejte nové uživatelské heslo", groups = {OnUpdateAccount.class})
	private String userPassword;
	
	@NotBlank(message = "Prosím zopakujte heslo pro kontrolu", groups = {OnUpdateAccount.class})
	private String confirmPassword;
	

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
}
