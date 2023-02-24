package com.oner365.cassandra.dao;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.oner365.cassandra.entity.Employee;

/**
 * Repository Employee
 * 
 * @author zhaoyong
 *
 */
@Repository
public interface IEmployeeDao extends CassandraRepository<Employee, Integer> {

}
