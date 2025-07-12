package com.danger.insurance.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.data.entities.RemovedContractsEntity;

public interface RemovedContractsRepository extends CrudRepository<RemovedContractsEntity, Long> {
}