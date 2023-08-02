package com.oner365.cassandra.dao;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.oner365.cassandra.entity.Employee;

/**
 * Repository Employee
 * 
 * @author zhaoyong
 *
 */
public interface IEmployeeDao extends CassandraRepository<Employee, Integer> {

}
