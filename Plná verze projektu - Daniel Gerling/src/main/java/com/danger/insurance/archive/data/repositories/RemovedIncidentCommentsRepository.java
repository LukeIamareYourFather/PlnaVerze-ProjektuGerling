package com.danger.insurance.archive.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.archive.data.entities.RemovedIncidentCommentsEntity;

public interface RemovedIncidentCommentsRepository extends CrudRepository<RemovedIncidentCommentsEntity, Long> {
}