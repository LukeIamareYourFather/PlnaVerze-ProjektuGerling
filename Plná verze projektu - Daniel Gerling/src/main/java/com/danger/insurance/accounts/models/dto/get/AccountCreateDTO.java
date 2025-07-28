package com.danger.insurance.accounts.models.dto.get;

import com.danger.insurance.accounts.data.enums.AccountRoles;
import com.danger.insurance.validation.groups.OnCreateAccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccountCreateDTO {

	@NotBlank(message = "Prosím zadejte uživatelský E-mail", groups = {OnCreateAccount.class})
	private String userEmail;
	
	@NotBlank(message = "Prosím zadejte uživatelské heslo", groups = {OnCreateAccount.class})
	private String userPassword;
	
	@NotBlank(message = "Prosím zopakujte uživatelské heslo pro kontrolu", groups = {OnCreateAccount.class})
	private String confirmPassword;
	
	@NotNull(message = "Prosím zadejte užiatleskou roli", groups = {OnCreateAccount.class})
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
