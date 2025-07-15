package com.danger.insurance.incidents.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.danger.insurance.incidents.data.entities.IncidentCommentsEntity;
import com.danger.insurance.incidents.models.dto.IncidentCommentsDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentCommentsCreatePostDTO;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

/**
 * MapStruct mapper interface for converting between {@link PartiesEntity} and {@link PartiesDetailsDTO}.
 * Handles object transformations and updates between entity and DTO layers.
 */
@Mapper(componentModel = "spring")
public interface IncidentCommentsMapper {
	 
	/**
     * Maps a {@link PartiesDetailsDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	IncidentCommentsEntity toEntity(IncidentCommentsDTO source);
	
	/**
     * Maps a {@link PartiesCreateDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	IncidentCommentsEntity newsCreateToEntity(IncidentCommentsDTO source);
	
	@Mapping(target = ".", source = "incidentCommentsCreatePostDTO")
	IncidentCommentsDTO mergeToIncidentCommentsDTO(@MappingTarget IncidentCommentsDTO incidentCommentsDTO, IncidentCommentsCreatePostDTO incidentCommentsCreatePostDTO);

	/**
     * Maps a {@link PartiesEntity} to a new {@link PartiesDetailsDTO}.
     *
     * @param source the entity containing party data.
     * @return a new {@link PartiesDetailsDTO} populated from the entity.
     */
	IncidentCommentsDTO toDTO(IncidentCommentsEntity source);			// Can be split into getDto and setDto in bigger projects
	
	/**
     * Updates an existing {@link PartiesDetailsDTO} with values from another DTO.
     *
     * @param source the source DTO with updated values.
     * @param target the target DTO to be updated.
     */
	void updateNewsDTO(IncidentCommentsDTO source, @MappingTarget IncidentCommentsDTO target);
	
	/**
     * Updates an existing {@link PartiesEntity} with values from a DTO.
     *
     * @param source the DTO containing new data.
     * @param target the entity to be updated.
     */
	void updateNewsEntity(IncidentCommentsDTO source, @MappingTarget IncidentCommentsEntity target);
}