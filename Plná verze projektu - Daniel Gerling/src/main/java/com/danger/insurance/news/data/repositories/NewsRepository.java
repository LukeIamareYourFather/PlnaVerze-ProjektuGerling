package com.danger.insurance.news.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danger.insurance.news.data.entities.NewsEntity;
import com.danger.insurance.parties.data.entities.PartiesEntity;

/**
 * Repository interface for accessing and querying {@link PartiesEntity} objects.
 * Provides standard CRUD methods and custom queries for advanced party lookups.
 */
public interface NewsRepository extends CrudRepository<NewsEntity, Long> {
}