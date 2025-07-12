package com.danger.insurance.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.danger.insurance.data.entities.ContractsEntity;
import com.danger.insurance.data.entities.PartiesEntity;
import com.danger.insurance.models.dto.insurances.ContractsDTO;
import com.danger.insurance.models.dto.parties.PartiesDetailsDTO;

@Mapper(componentModel = "spring")
public interface ContractsMapper {

	/**
     * Maps a {@link PartiesDetailsDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	ContractsEntity toEntity(ContractsDTO source);
	
	/**
     * Maps a {@link PartiesEntity} to a new {@link PartiesDetailsDTO}.
     *
     * @param source the entity containing party data.
     * @return a new {@link PartiesDetailsDTO} populated from the entity.
     */
	ContractsDTO toDTO(ContractsEntity source);			// Can be split into getDto and setDto in bigger projects
	
	/**
     * Updates an existing {@link PartiesDetailsDTO} with values from another DTO.
     *
     * @param source the source DTO with updated values.
     * @param target the target DTO to be updated.
     */
	void updatePartiesSearchDTO(ContractsDTO source, @MappingTarget ContractsDTO target);
	
	/**
     * Updates an existing {@link PartiesEntity} with values from a DTO.
     *
     * @param source the DTO containing new data.
     * @param target the entity to be updated.
     */
	void updatePartiesEntity(ContractsDTO source, @MappingTarget ContractsEntity target);
}