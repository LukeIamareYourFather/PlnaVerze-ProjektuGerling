package com.danger.insurance.incidents.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.danger.insurance.incidents.data.entities.IncidentsEntity;
import com.danger.insurance.incidents.models.dto.IncidentsDTO;
import com.danger.insurance.incidents.models.dto.get.IncidentDetailsGetDTO;
import com.danger.insurance.incidents.models.dto.post.IncidentsCreatePostDTO;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

/**
 * MapStruct mapper interface for converting between {@link PartiesEntity} and {@link PartiesDetailsDTO}.
 * Handles object transformations and updates between entity and DTO layers.
 */
@Mapper(componentModel = "spring")
public interface IncidentsMapper {
	 
	/**
     * Maps a {@link PartiesDetailsDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	IncidentsEntity toEntity(IncidentsDTO source);
	
	/**
     * Maps a {@link PartiesCreateDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	IncidentsEntity incidentCreateToEntity(IncidentsDTO source);
	
	IncidentDetailsGetDTO toDetailsToDTO(IncidentsEntity source);			// Can be split into getDto and setDto in bigger projects
	
	@Mapping(target = ".", source = "incidentsCreateDTO")
	IncidentsDTO mergeCreatePostDTOToIncidentsDTO(@MappingTarget IncidentsDTO incidentsDTO, IncidentsCreatePostDTO incidentsCreateDTO);

	@Mapping(target = ".", source = "incidentDetailsGetDTO")
	IncidentsDTO mergeDetailsDTOToIncidentsDTO(@MappingTarget IncidentsDTO incidentsDTO, IncidentDetailsGetDTO incidentDetailsGetDTO);

	/**
     * Maps a {@link PartiesEntity} to a new {@link PartiesDetailsDTO}.
     *
     * @param source the entity containing party data.
     * @return a new {@link PartiesDetailsDTO} populated from the entity.
     */
	IncidentsDTO toDTO(IncidentsEntity source);			// Can be split into getDto and setDto in bigger projects
	
	
	/**
     * Updates an existing {@link PartiesDetailsDTO} with values from another DTO.
     *
     * @param source the source DTO with updated values.
     * @param target the target DTO to be updated.
     */
	void updateNewsDTO(IncidentsDTO source, @MappingTarget IncidentsDTO target);
	
	/**
     * Updates an existing {@link PartiesEntity} with values from a DTO.
     *
     * @param source the DTO containing new data.
     * @param target the entity to be updated.
     */
	void updateNewsEntity(IncidentsDTO source, @MappingTarget IncidentsEntity target);
}