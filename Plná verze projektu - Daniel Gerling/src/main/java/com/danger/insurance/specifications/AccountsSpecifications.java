package com.danger.insurance.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.danger.insurance.accounts.data.entities.AccountsEntity;
import com.danger.insurance.accounts.data.enums.AccountRoles;

import jakarta.persistence.criteria.Predicate;

public class AccountsSpecifications {

	@SuppressWarnings("unused")
	public static Specification<AccountsEntity> dynamicAccountSearch(String userEmail, AccountRoles userRole) {
        
		return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userEmail != null) {
                predicates.add(cb.equal(root.get("userEmail"), userEmail));
            }

            if (userRole != null) {
                predicates.add(cb.equal(root.get("userRole"), userRole));
            }
        

            // If no fields were provided, return an always-false predicate
            if (predicates.isEmpty()) {
                return cb.disjunction(); 				// prevents returning all entries
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
    }
	
}