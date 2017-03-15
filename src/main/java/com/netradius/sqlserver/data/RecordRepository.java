package com.netradius.sqlserver.data;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * JPA repository used to run native queries for testing.
 *
 * @author Abhinav Nahar
 */
public interface RecordRepository extends PagingAndSortingRepository<Record, UUID>{

	Record findByName(String name);

	@Transactional
	@Modifying
	@Query(value = "insert into record(name) VALUES (?1)", nativeQuery = true)
	int insertRecord(String name);

	@Query(value = "select id from record where  name = ?1", nativeQuery = true)
	String selectRecord(String name);

}