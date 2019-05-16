package com.usa.nj.gov.uhip.admin.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.usa.nj.gov.uhip.admin.entity.UserAccountEntity;

/**
 * This interface is used to perform CURD operation on USER_ACCOUNTS table on
 * dao
 * 
 * @author DEVENDRA
 *
 */

@Repository("userAccRepo")
public interface UserAccountRepository extends JpaRepository<UserAccountEntity, Serializable> {
	@Query(value = "select count(email) from UserAccountEntity where email=:email")
	public Integer findByEmail(String email);
}
