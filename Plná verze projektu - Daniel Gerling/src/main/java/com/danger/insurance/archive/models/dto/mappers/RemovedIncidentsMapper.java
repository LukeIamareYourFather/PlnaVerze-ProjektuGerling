package com.danger.insurance.archive.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.danger.insurance.archive.data.entities.RemovedIncidentsEntity;
import com.danger.insurance.archive.models.dto.RemoveIncidentReasonsDTO;
import com.danger.insurance.archive.models.dto.RemovedIncidentsDTO;
import com.danger.insurance.incidents.models.dto.IncidentsDTO;
import com.danger.insurance.parties.data.entities.PartiesEntity;
import com.danger.insurance.parties.models.dto.PartiesCreateDTO;
import com.danger.insurance.parties.models.dto.PartiesDetailsDTO;

@Mapper(componentModel = "spring")
public interface RemovedIncidentsMapper {

	/**
     * Maps a {@link PartiesDetailsDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	RemovedIncidentsEntity toEntity(RemovedIncidentsDTO source);
	
	/**
     * Maps a {@link PartiesCreateDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	RemovedIncidentsEntity removedIncidentsToEntity(RemovedIncidentsDTO source);
	
	/**
     * Maps a {@link PartiesEntity} to a new {@link PartiesDetailsDTO}.
     *
     * @param source the entity containing party data.
     * @return a new {@link PartiesDetailsDTO} populated from the entity.
     */
	RemovedIncidentsDTO toDTO(RemovedIncidentsEntity source);			// Can be split into getDto and setDto in bigger projects
	
	@Mapping(target = ".", source = "incidentsDTO")
    @Mapping(target = ".",source = "removeIncidentReasonsDTO")
	RemovedIncidentsDTO mergeToRemovedIncidentsDTO (IncidentsDTO incidentsDTO, RemoveIncidentReasonsDTO removeIncidentReasonsDTO);
	
	/**
     * Updates an existing {@link PartiesDetailsDTO} with values from another DTO.
     *
     * @param source the source DTO with updated values.
     * @param target the target DTO to be updated.
     */
	void updateRemovedIncidentsDTO(RemovedIncidentsDTO source, @MappingTarget RemovedIncidentsDTO target);
	
	/**
     * Updates an existing {@link PartiesEntity} with values from a DTO.
     *
     * @param source the DTO containing new data.
     * @param target the entity to be updated.
     */
	void updateRemovedIncidentsEntity(RemovedIncidentsDTO source, @MappingTarget RemovedIncidentsEntity target);
}