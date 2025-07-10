package com.danger.insurance.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.danger.insurance.data.entities.InsurancesEntity;
import com.danger.insurance.data.entities.PartiesEntity;
import com.danger.insurance.models.dto.insurances.InsurancesDTO;
import com.danger.insurance.models.dto.parties.PartiesDetailsDTO;

@Mapper(componentModel = "spring")
public interface InsurancesMapper {

	/**
     * Maps a {@link PartiesDetailsDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	InsurancesEntity toEntity(InsurancesDTO source);
	
	/**
     * Maps a {@link PartiesEntity} to a new {@link PartiesDetailsDTO}.
     *
     * @param source the entity containing party data.
     * @return a new {@link PartiesDetailsDTO} populated from the entity.
     */
	InsurancesDTO toDTO(InsurancesEntity source);			// Can be split into getDto and setDto in bigger projects
	
	/**
     * Updates an existing {@link PartiesDetailsDTO} with values from another DTO.
     *
     * @param source the source DTO with updated values.
     * @param target the target DTO to be updated.
     */
	void updatePartiesSearchDTO(InsurancesDTO source, @MappingTarget InsurancesDTO target);
	
	/**
     * Updates an existing {@link PartiesEntity} with values from a DTO.
     *
     * @param source the DTO containing new data.
     * @param target the entity to be updated.
     */
	void updatePartiesEntity(InsurancesDTO source, @MappingTarget InsurancesEntity target);
}