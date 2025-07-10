package com.danger.insurance.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.data.entities.DeletedPartiesEntity;

public interface DeletedPartiesRepository extends CrudRepository<DeletedPartiesEntity, Long> {

}