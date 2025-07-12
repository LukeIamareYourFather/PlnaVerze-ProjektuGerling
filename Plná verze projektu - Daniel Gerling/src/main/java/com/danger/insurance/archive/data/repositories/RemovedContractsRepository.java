package com.danger.insurance.archive.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.archive.data.entities.RemovedContractsEntity;

public interface RemovedContractsRepository extends CrudRepository<RemovedContractsEntity, Long> {
}