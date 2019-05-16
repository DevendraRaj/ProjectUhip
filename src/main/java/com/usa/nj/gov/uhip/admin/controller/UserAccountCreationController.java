package com.usa.nj.gov.uhip.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.usa.nj.gov.uhip.admin.model.UserAccount;
import com.usa.nj.gov.uhip.admin.service.UserAccountService;
import com.usa.nj.gov.uhip.constants.AppConstants;
import com.usa.nj.gov.uhip.util.UHIPAppProperties;

@Controller
public class UserAccountCreationController {

	private static final Logger logger = LoggerFactory.getLogger(UserAccountCreationController.class);
	// 2 method are required
	// 1 method to load for GET request
	// 2 method to submit form handling POST request

	@Autowired
	private UserAccountService userAccService;

	@Autowired
	private UHIPAppProperties properties;

	/**
	 * This method is used to load User Account Creation Form
	 * 
	 * @param model
	 * @return String
	 * @author DEVENDRA
	 */
	@RequestMapping(value = { "/", "createUserAcc" }, method = RequestMethod.GET)

	public String loadAccCreationForm(Model model) {
		logger.debug("*** loadAccCreationForm() started***");
		UserAccount acc = new UserAccount();
		model.addAttribute(AppConstants.USER_ACC_MODEL, acc);

		initializeFormValues(model);
		logger.debug("*** loadAccCreationForm() ended***");
		logger.debug("*** loadAccCreationForm() completed***");
		return AppConstants.USER_ACC_CREATION_VIEW;// logical view
	}

	/**
	 * This method is used to User Account creation
	 * 
	 * @param userAcc
	 * @return String
	 * @author DEVENDRA
	 */
	@RequestMapping(value = { "/", "createUserAcc" }, method = RequestMethod.POST)
	public String createUserAccount(@ModelAttribute("userAccModel") UserAccount userAcc,
			RedirectAttributes redirectAttrs) {
		logger.info("Form Data ::" + userAcc);
		boolean isSaved = userAccService.createUserAccount(userAcc);

		Map<String, String> props = properties.getUhipProps();
		if (isSaved) {
			// success msg
			String msg = props.get("accCreationSuccess");
			// model.addAttribute("succMsg", msg);
			redirectAttrs.addFlashAttribute("succMsg", msg);
		} else {
			// failure msg
			String msg = props.get("accCreationFailure");
			// model.addAttribute("failMsg", msg);
			redirectAttrs.addFlashAttribute("failMsg", msg);
		}
		// return AppConstants.USER_ACC_CREATION_VIEW;// logical view
		return "redirect:/accRegSuccess";
	}

	/**
	 * This method is used to display success msg
	 * 
	 * @param model
	 * @return String
	 * @author DEVENDRA
	 */
	@RequestMapping(value = "/accRegSuccess", method = RequestMethod.GET)
	public String accRegSuccess(Model model) {
		logger.info("*** accRegSuccess() called***");

		UserAccount acc = new UserAccount();
		model.addAttribute(AppConstants.USER_ACC_MODEL, acc);
		initializeFormValues(model);
		return AppConstants.USER_ACC_CREATION_VIEW;// logical view

	}

	/**
	 * This method is used to initialize Form Values
	 * 
	 * @param model
	 * @author DEVENDRA
	 */
	public void initializeFormValues(Model model) {
		List<String> gendersList = new ArrayList();
		gendersList.add("Male");
		gendersList.add("Fe-male");
		model.addAttribute(AppConstants.GRNDERS_LIST, gendersList);

		List<String> roleList = new ArrayList<>();
		roleList.add("Case-Worker");
		roleList.add("Admin");
		model.addAttribute(AppConstants.ROLES_LIST, roleList);

	}

	/**
	 * This method is used to validate Email
	 * 
	 * @param req
	 * @return String
	 * @author DEVENDRA
	 */
	@RequestMapping(value = "/checkEmail", method = RequestMethod.GET)

	public @ResponseBody String checkEmail(HttpServletRequest req) {
		logger.debug("***Email Validation Started ***");
		String email = req.getParameter("email");
		// System.out.println(email);

		if (email == null || "".equals(email)) {
			logger.error("***Email is Missing in response ***");

		}

		String response = userAccService.validateEmail(email);
		logger.info("***Email Validation completed successfully ***");

		return response;

	}
}
