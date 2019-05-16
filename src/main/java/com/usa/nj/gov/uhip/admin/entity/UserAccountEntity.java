package com.usa.nj.gov.uhip.admin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "USER_ACCOUNT")
@Data
public class UserAccountEntity {
	
	@Id
	@SequenceGenerator(
			sequenceName = "user_acc_seq",
			initialValue=1,
			allocationSize=1,
			name="user_acc_seq_gen"			
			)
	@GeneratedValue(generator ="user_acc_seq_gen" )
	
	@Column(name="USER_ACC_ID")
	private Integer userAccId;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="USER_EMAIL")
	private String email;
	
	@Column(name="USER_PWD")
	private String password;
	
	@Column(name="USER_DOB")
	private String dob;
	
	@Column(name="USER_GENDER")
	private String gender;
	
	@Column(name="SSN")
	private Long ssn;
	
	@Column(name="USER_ROLE")
	private String role;
	
	@Column(name="ACTIVE_SW")
	private String activeSw;
	
	@Column(name="CREATE_DT")
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name="UPDATE_DT")
	@UpdateTimestamp
	private Date updatedDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;

}
