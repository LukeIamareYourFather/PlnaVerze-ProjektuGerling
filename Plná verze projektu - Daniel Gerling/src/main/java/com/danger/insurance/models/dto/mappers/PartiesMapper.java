package com.danger.insurance.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.danger.insurance.data.entities.PartiesEntity;
import com.danger.insurance.models.dto.parties.PartiesCreateDTO;
import com.danger.insurance.models.dto.parties.PartiesDetailsDTO;

/**
 * MapStruct mapper interface for converting between {@link PartiesEntity} and {@link PartiesDetailsDTO}.
 * Handles object transformations and updates between entity and DTO layers.
 */
@Mapper(componentModel = "spring")
public interface PartiesMapper {
	 
	/**
     * Maps a {@link PartiesDetailsDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	PartiesEntity toEntity(PartiesDetailsDTO source);
	
	/**
     * Maps a {@link PartiesCreateDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	PartiesEntity partiesCreateToEntity(PartiesCreateDTO source);
	
	/**
     * Maps a {@link PartiesEntity} to a new {@link PartiesDetailsDTO}.
     *
     * @param source the entity containing party data.
     * @return a new {@link PartiesDetailsDTO} populated from the entity.
     */
	PartiesDetailsDTO toDTO(PartiesEntity source);			// Can be split into getDto and setDto in bigger projects
	
	/**
     * Updates an existing {@link PartiesDetailsDTO} with values from another DTO.
     *
     * @param source the source DTO with updated values.
     * @param target the target DTO to be updated.
     */
	void updatePartiesSearchDTO(PartiesDetailsDTO source, @MappingTarget PartiesDetailsDTO target);
	
	/**
     * Updates an existing {@link PartiesEntity} with values from a DTO.
     *
     * @param source the DTO containing new data.
     * @param target the entity to be updated.
     */
	void updatePartiesEntity(PartiesDetailsDTO source, @MappingTarget PartiesEntity target);
}