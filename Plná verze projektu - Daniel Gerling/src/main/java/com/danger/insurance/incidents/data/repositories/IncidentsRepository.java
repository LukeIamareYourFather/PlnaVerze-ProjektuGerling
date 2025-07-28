package com.danger.insurance.incidents.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.danger.insurance.incidents.data.entities.IncidentsEntity;
import com.danger.insurance.incidents.data.enums.IncidentStatus;
import com.danger.insurance.incidents.data.enums.IncidentType;
import com.danger.insurance.parties.data.entities.PartiesEntity;

/**
 * Repository interface for accessing and querying {@link PartiesEntity} objects.
 * Provides standard CRUD methods and custom queries for advanced party lookups.
 */
public interface IncidentsRepository extends JpaRepository<IncidentsEntity, Long>, JpaSpecificationExecutor<IncidentsEntity> {
	
	@Query("SELECT COUNT(i) FROM IncidentsEntity i")
	long getTotalIncidentsCount();

	@Query("SELECT COUNT(i) FROM IncidentsEntity i WHERE i.currentStatus = :incidentStatus")
    long getTotalStatusIncidentCount(@Param("incidentStatus") IncidentStatus incidentStatus);
	
	@Query("SELECT COUNT(i) FROM IncidentsEntity i WHERE i.incidentType = :incidentType")
    long getTotalIncidentTypeCount(@Param("incidentType") IncidentType incidentType);
}