package com.usa.nj.gov.uhip.admin.model;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class UserAccount {

	private Integer userAccId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	@DateTimeFormat(pattern = "dd/MMM/yyyy")
	private String dob;
	private String gender;
	private Long ssn;
	private String role;
	private String activeSw;

	private Timestamp createdDate;
	private Timestamp updatedDate;
	private String createdBy;
	private String updatedBy;
}