package com.danger.insurance.archive.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.danger.insurance.archive.data.entities.RemovedContractsEntity;
import com.danger.insurance.archive.models.dto.RemoveContractReasonsDTO;
import com.danger.insurance.insurances.contracts.models.dto.ContractsDTO;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

@Mapper(componentModel = "spring")
public interface RemovedContractsMapper {

	/**
     * Maps a {@link PartiesDetailsDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	RemovedContractsEntity toEntity(RemoveContractReasonsDTO source);
	
	/**
     * Maps a {@link PartiesEntity} to a new {@link PartiesDetailsDTO}.
     *
     * @param source the entity containing party data.
     * @return a new {@link PartiesDetailsDTO} populated from the entity.
     */
	RemoveContractReasonsDTO toDTO(RemovedContractsEntity source);			// Can be split into getDto and setDto in bigger projects
	
	@Mapping(target = ".", source = "contractsDTO")
	@Mapping(target = ".", source = "insurancesDTO")
	RemoveContractReasonsDTO mergeToRemoveContractReasonsDTO(ContractsDTO contractsDTO, InsurancesDTO insurancesDTO, @MappingTarget RemoveContractReasonsDTO removeContractReasonsDTO);
	
	/**
     * Updates an existing {@link PartiesDetailsDTO} with values from another DTO.
     *
     * @param source the source DTO with updated values.
     * @param target the target DTO to be updated.
     */
	void updatePartiesSearchDTO(RemoveContractReasonsDTO source, @MappingTarget RemoveContractReasonsDTO target);
	
	/**
     * Updates an existing {@link PartiesEntity} with values from a DTO.
     *
     * @param source the DTO containing new data.
     * @param target the entity to be updated.
     */
	void updatePartiesEntity(RemoveContractReasonsDTO source, @MappingTarget RemovedContractsEntity target);
}