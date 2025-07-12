package com.danger.insurance.archive.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.archive.data.entities.DeletedPartiesEntity;

public interface DeletedPartiesRepository extends CrudRepository<DeletedPartiesEntity, Long> {

}