package com.danger.insurance.archive.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.danger.insurance.archive.data.entities.DeletedInsurancesEntity;
import com.danger.insurance.archive.models.dto.DeleteInsurancesReasonsDTO;
import com.danger.insurance.archive.models.dto.DeletedInsurancesDTO;
import com.danger.insurance.insurances.models.dto.InsurancesDTO;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

@Mapper(componentModel = "spring")
public interface DeletedInsurancesMapper {

	/**
     * Maps a {@link PartiesDetailsDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	DeletedInsurancesEntity toEntity(DeletedInsurancesDTO source);
	
	/**
     * Maps a {@link PartiesCreateDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	DeletedInsurancesEntity partiesCreateToEntity(DeletedInsurancesDTO source);
	
	/**
     * Maps a {@link PartiesEntity} to a new {@link PartiesDetailsDTO}.
     *
     * @param source the entity containing party data.
     * @return a new {@link PartiesDetailsDTO} populated from the entity.
     */
	DeletedInsurancesDTO toDTO(DeletedInsurancesEntity source);			// Can be split into getDto and setDto in bigger projects
	
	@Mapping(target = ".", source = "reasonsDto")
    @Mapping(target = ".",source = "insurancesDTO")
	DeletedInsurancesDTO mergeToDeleteDTO(DeleteInsurancesReasonsDTO reasonsDto, InsurancesDTO insurancesDTO);

	/**
     * Updates an existing {@link PartiesDetailsDTO} with values from another DTO.
     *
     * @param source the source DTO with updated values.
     * @param target the target DTO to be updated.
     */
	void updatePartiesSearchDTO(DeletedInsurancesDTO source, @MappingTarget DeletedInsurancesDTO target);
	
	/**
     * Updates an existing {@link PartiesEntity} with values from a DTO.
     *
     * @param source the DTO containing new data.
     * @param target the entity to be updated.
     */
	void updatePartiesEntity(DeletedInsurancesDTO source, @MappingTarget DeletedInsurancesEntity target);
}