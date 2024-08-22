package com.oner365.postgis.repository;

import org.springframework.data.repository.CrudRepository;

import com.oner365.postgis.entity.Position;

/**
 * Position Repository
 * 
 * @author zhaoyong
 */
public interface IPositionRepository extends CrudRepository<Position, String> {

}
