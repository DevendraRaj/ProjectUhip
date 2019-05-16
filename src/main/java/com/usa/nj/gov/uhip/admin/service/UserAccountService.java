package com.usa.nj.gov.uhip.admin.service;

import org.springframework.ui.Model;

import com.usa.nj.gov.uhip.admin.model.UserAccount;

public interface UserAccountService {

	public boolean createUserAccount(UserAccount userAccount);

	public String validateEmail(String email);

}
