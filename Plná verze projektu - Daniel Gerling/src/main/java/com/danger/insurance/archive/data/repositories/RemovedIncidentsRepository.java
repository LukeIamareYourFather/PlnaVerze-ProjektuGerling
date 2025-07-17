package com.danger.insurance.archive.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.archive.data.entities.RemovedIncidentsEntity;

public interface RemovedIncidentsRepository extends CrudRepository<RemovedIncidentsEntity, Long> {
}