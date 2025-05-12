package com.ecommerceapp.commonmodule.base.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<T, Tid> extends CrudRepository<T, Tid> {
}
