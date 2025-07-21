package com.danger.insurance.accounts.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.danger.insurance.accounts.data.entities.AccountsEntity;
import com.danger.insurance.accounts.models.dto.AccountCreateDTO;
import com.danger.insurance.accounts.models.dto.AccountsDTO;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

@Mapper(componentModel = "spring")
public interface AccountsMapper {
	
	/**
     * Maps a {@link PartiesCreateDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	AccountsEntity accountsDTOToEntity(AccountsDTO source);
	
	/**
     * Maps a {@link PartiesEntity} to a new {@link PartiesDetailsDTO}.
     *
     * @param source the entity containing party data.
     * @return a new {@link PartiesDetailsDTO} populated from the entity.
     */
	AccountsDTO accountsEntityToAccountsDTO(AccountsEntity source);			// Can be split into getDto and setDto in bigger projects
	
	@Mapping(target = ".", source = "accountCreateDTO")
	AccountsDTO mergeToAccountsDTO(@MappingTarget AccountsDTO accountsDTO, AccountCreateDTO accountCreateDTO);
	
	/**
     * Updates an existing {@link PartiesDetailsDTO} with values from another DTO.
     *
     * @param source the source DTO with updated values.
     * @param target the target DTO to be updated.
     */
	void updateAccountsDTO(AccountsDTO source, @MappingTarget AccountsDTO target);
	
	/**
     * Updates an existing {@link PartiesEntity} with values from a DTO.
     *
     * @param source the DTO containing new data.
     * @param target the entity to be updated.
     */
	void updateAccountsEntity(AccountsDTO source, @MappingTarget AccountsEntity target);
}