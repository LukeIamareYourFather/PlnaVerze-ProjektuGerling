package com.danger.insurance.models.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.danger.insurance.data.entities.DeletedPartiesEntity;
import com.danger.insurance.data.entities.PartiesEntity;
import com.danger.insurance.models.dto.parties.DeletedPartiesDTO;
import com.danger.insurance.models.dto.parties.PartiesCreateDTO;
import com.danger.insurance.models.dto.parties.PartiesDetailsDTO;
import com.danger.insurance.models.dto.parties.PartiesReasonsFormDTO;

@Mapper(componentModel = "spring")
public interface DeletedPartiesMapper {

	/**
     * Maps a {@link PartiesDetailsDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	DeletedPartiesEntity toEntity(DeletedPartiesDTO source);
	
	/**
     * Maps a {@link PartiesCreateDTO} to a new {@link PartiesEntity}.
     *
     * @param source the DTO containing party data.
     * @return a new {@link PartiesEntity} populated from the DTO.
     */
	DeletedPartiesEntity partiesCreateToEntity(DeletedPartiesDTO source);
	
	/**
     * Maps a {@link PartiesEntity} to a new {@link PartiesDetailsDTO}.
     *
     * @param source the entity containing party data.
     * @return a new {@link PartiesDetailsDTO} populated from the entity.
     */
	DeletedPartiesDTO toDTO(DeletedPartiesEntity source);			// Can be split into getDto and setDto in bigger projects
	
	@Mapping(target = ".", source = "reasonsDto")
    @Mapping(target = ".", source = "partiesDTO")
	DeletedPartiesDTO mergeToDeleteDTO(PartiesReasonsFormDTO reasonsDto, PartiesDetailsDTO partiesDTO);

	/**
     * Updates an existing {@link PartiesDetailsDTO} with values from another DTO.
     *
     * @param source the source DTO with updated values.
     * @param target the target DTO to be updated.
     */
	void updatePartiesSearchDTO(DeletedPartiesDTO source, @MappingTarget DeletedPartiesDTO target);
	
	/**
     * Updates an existing {@link PartiesEntity} with values from a DTO.
     *
     * @param source the DTO containing new data.
     * @param target the entity to be updated.
     */
	void updatePartiesEntity(DeletedPartiesDTO source, @MappingTarget DeletedPartiesEntity target);
}