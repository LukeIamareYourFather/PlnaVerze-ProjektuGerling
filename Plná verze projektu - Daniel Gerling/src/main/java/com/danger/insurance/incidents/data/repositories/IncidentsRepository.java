package com.danger.insurance.incidents.data.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.danger.insurance.incidents.data.entities.IncidentsEntity;
import com.danger.insurance.incidents.data.enums.IncidentStatus;
import com.danger.insurance.incidents.data.enums.IncidentType;
import com.danger.insurance.insurances.data.enums.InsurancesSubjects;
import com.danger.insurance.parties.data.entities.PartiesEntity;

/**
 * Repository interface for accessing and querying {@link PartiesEntity} objects.
 * Provides standard CRUD methods and custom queries for advanced party lookups.
 */
public interface IncidentsRepository extends CrudRepository<IncidentsEntity, Long> {
	
	@Query("""
				SELECT i FROM IncidentsEntity i
				WHERE (:caseNumber IS NULL OR i.caseNumber = :caseNumber)
			   	AND (:birthNumber IS NULL OR i.birthNumber = :birthNumber)
				AND (:title IS NULL OR i.title = :title)
				AND (:incidentType IS NULL OR i.incidentType = :incidentType)
			  	AND (:incidentSubject IS NULL OR i.incidentSubject = :incidentSubject)
			   	AND (:currentStatus IS NULL OR i.currentStatus = :currentStatus)
			   	AND (:accidentDate IS NULL OR i.accidentDate = :accidentDate)
			""")
	List<IncidentsEntity> searchIncidents(
			@Param("caseNumber") String caseNumber,
			@Param("birthNumber") String birthNumber,
	   		@Param("title") String title,
	   		@Param("incidentType") IncidentType incidentType,
	    	@Param("incidentSubject") InsurancesSubjects incidentSubject,
	    	@Param("currentStatus") IncidentStatus currentStatus,
	    	@Param("accidentDate") LocalDate accidentDate
		);
	
	@Query("SELECT COUNT(i) FROM IncidentsEntity i")
	long getTotalIncidentsCount();

	@Query("SELECT COUNT(i) FROM IncidentsEntity i WHERE i.currentStatus = :incidentStatus")
    long getTotalStatusIncidentCount(@Param("incidentStatus") IncidentStatus incidentStatus);
	
	@Query("SELECT COUNT(i) FROM IncidentsEntity i WHERE i.incidentType = :incidentType")
    long getTotalIncidentTypeCount(@Param("incidentType") IncidentType incidentType);
}