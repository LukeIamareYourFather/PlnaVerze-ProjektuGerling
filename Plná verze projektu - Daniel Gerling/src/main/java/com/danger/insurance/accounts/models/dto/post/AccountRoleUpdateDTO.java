package com.danger.insurance.accounts.models.dto.post;

import com.danger.insurance.accounts.data.enums.AccountRoles;
import com.danger.insurance.validation.groups.OnUpdateAccount;

import jakarta.validation.constraints.NotNull;

public class AccountRoleUpdateDTO {
	
	@NotNull(message = "Prosím zadejte novou uživatelskou roli", groups = {OnUpdateAccount.class})
	private AccountRoles userRole;

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
