package com.danger.insurance.archive.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.danger.insurance.archive.data.entities.RemovedIncidentCommentsEntity;
import com.danger.insurance.archive.models.dto.RemovedIncidentCommentsDTO;
import com.danger.insurance.incidents.models.dto.IncidentCommentsDTO;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

@Mapper(componentModel = "spring")
public interface RemovedIncidentCommentsMapper {

	/**
     * Maps a {@link PartiesDetailsDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	RemovedIncidentCommentsEntity toEntity(RemovedIncidentCommentsDTO source);
	
	/**
     * Maps a {@link PartiesCreateDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	RemovedIncidentCommentsEntity removedIncidentCommentsToEntity(RemovedIncidentCommentsDTO source);
	
	/**
     * Maps a {@link PartiesEntity} to a new {@link PartiesDetailsDTO}.
     *
     * @param source the entity containing party data.
     * @return a new {@link PartiesDetailsDTO} populated from the entity.
     */
	RemovedIncidentCommentsDTO toDTO(RemovedIncidentCommentsEntity source);			// Can be split into getDto and setDto in bigger projects
	
	@Mapping(target = ".", source = "incidentCommentsDTO")
	RemovedIncidentCommentsDTO mergeToRemovedIncidentCommentsDTO(@MappingTarget RemovedIncidentCommentsDTO removedIncidentCommentsDTO, IncidentCommentsDTO incidentCommentsDTO);
	
	/**
     * Updates an existing {@link PartiesDetailsDTO} with values from another DTO.
     *
     * @param source the source DTO with updated values.
     * @param target the target DTO to be updated.
     */
	void updateRemovedIncidentCommentsDTO(RemovedIncidentCommentsDTO source, @MappingTarget RemovedIncidentCommentsDTO target);
	
	/**
     * Updates an existing {@link PartiesEntity} with values from a DTO.
     *
     * @param source the DTO containing new data.
     * @param target the entity to be updated.
     */
	void updateRemovedIncidentCommentsEntity(RemovedIncidentCommentsDTO source, @MappingTarget RemovedIncidentCommentsEntity target);
}