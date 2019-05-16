package com.usa.nj.gov.uhip.admin.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usa.nj.gov.uhip.admin.entity.UserAccountEntity;
import com.usa.nj.gov.uhip.admin.model.UserAccount;
import com.usa.nj.gov.uhip.admin.repository.UserAccountRepository;
import com.usa.nj.gov.uhip.constants.AppConstants;
import com.usa.nj.gov.uhip.exception.AdminException;
import com.usa.nj.gov.uhip.util.MailService;
import com.usa.nj.gov.uhip.util.UHIPAppProperties;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	private static final Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);

	@Autowired
	UserAccountRepository userAccRepo;

	@Autowired
	MailService mailService;

	@Autowired
	private UHIPAppProperties props;

	/**
	 * This Method is used to create Account for one user
	 */
	@Override
	public boolean createUserAccount(UserAccount userAccount) {
		logger.debug("***createUserAccount()  started ***");
		boolean isSaved = false;
		try {
			// Copying in the properties
			UserAccountEntity entity = new UserAccountEntity();
			BeanUtils.copyProperties(userAccount, entity);

			// setting Active switch for new user account
			entity.setActiveSw(AppConstants.ACTIVE_SW);

			// calling persistence layer method
			entity = userAccRepo.save(entity);

			if (entity.getUserAccId() > 0)
				sendAccCreationEmail(userAccount);

			isSaved = true;

		} catch (Exception e) {
			logger.error("Exception occured in createUserAccount()");
			throw new AdminException(e.getMessage());

		}
		logger.debug("*** createUserAccount() ended ***");
		return isSaved;
	}

	/**
	 * 
	 */
	@Override
	public String validateEmail(String email) {
		logger.debug("*** validateEmail() started ***");
		Integer cnt = userAccRepo.findByEmail(email);

		if (cnt >= 1) {
			logger.warn("*** Duplicate Email Found ***");
		}
		logger.debug("*** validateEmail() ended ***");
		logger.info("*** validateEmail() completed successfully ***");

		if (cnt >= 1) {
			return "Duplicate";
		}
		return "Unique";

		// return (cnt >= 1) ? AppConstants.DUPLICATE :AppConstants.UNIQUE;
	}

	private void sendAccCreationEmail(UserAccount accModel) {

		String subject = props.getUhipProps().get(AppConstants.ACC_CREATION_UHIP_SUB);
		// logic to send email

		String body = getAccCreationEmailBodyContent(accModel);
		mailService.sendAccRegEmail(accModel.getEmail(), subject, body);
	}

	/**
	 * This method is used to read email body content for a file and formate
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getAccCreationEmailBodyContent(UserAccount accModel) {

		String fileName = "ACC_CREATION_EMAIL_BODY_TEMPLATE.txt";
		StringBuffer sb = new StringBuffer("");
		FileReader fr = null;
		String line = null;
		BufferedReader br = null;
		try {

			// FileReader is read character by character
			fr = new FileReader(fileName);
			// BufferedReaderis read line by line data
			br = new BufferedReader(fr);

			line = br.readLine();

			while (line != null) {
				// process line
				if (line.equals("") || !line.contains("$")) {
					sb.append(line);
					line = br.readLine();
					continue;
				}
				if (line.contains("${FNAME}")) {
					line = line.replace("${FNAME}", accModel.getFirstName());
				}
				if (line.contains("${LNAME}")) {
					line = line.replace("${LNAME}", accModel.getLastName());
				}
				if (line.contains("${URL}")) {
					line = line.replace("${URL}", "<a href=\"localhost:6060:UHIP\"> www.uhip.com</a>");
				}
				if (line.contains("${EMAIL}")) {
					line = line.replace("${EMAIL}", accModel.getEmail());
				}
				if (line.contains("${PWD}")) {
					line = line.replace("${PWD}", accModel.getPassword());
				}
				if (line.contains("${ROLE}")) {
					line = line.replace("${ROLE}", accModel.getRole());
				}
				if (line.contains("${PHNO}")) {
					String phno = props.getUhipProps().get(AppConstants.UHIP_ADMIN_PHNO);
					line = line.replace("${PHNO}", phno);
				}
				sb.append(line);
				// read next line

				line = br.readLine();
			}
		} catch (Exception e) {
			logger.error("***Exception occured  while reading Email Body ***");
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return sb.toString();

	}
}
