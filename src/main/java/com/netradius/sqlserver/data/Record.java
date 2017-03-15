package com.netradius.sqlserver.data;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Example Record hibernate bean.
 *
 * @author Abhinav Nahar
 */
@Data
@Entity
@Table(name = "record")
public class Record {

	@Id
	@Column(name = "id", columnDefinition="uniqueidentifier")
	@Type(type = "com.netradius.sqlserver.data.GuidType")
	private UUID id;

	@Column(name = "name")
	private String name;
}
