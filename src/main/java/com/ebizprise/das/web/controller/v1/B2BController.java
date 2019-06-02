package com.ebizprise.das.web.controller.v1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebizprise.das.form.base.PriveAuthForm;
import com.ebizprise.das.scheduled.service.weather.B2BService;

/**
 * description:
 * 
 * @author Jacky
 * @date 21/12/2018 18:20 PM
 * @email jacky.lee@ebizprise.com
 */

@Controller
@RequestMapping("/")
public class B2BController {
	private static final Logger logger = LoggerFactory
			.getLogger(B2BController.class);

	@Autowired
	private B2BService b2BService;

	@RequestMapping("/readPortfolioNavLatest")
	public @ResponseBody List readPortfolioNavLatest(
			@RequestParam(value = "etlDate", required = false) String etlDate,
			@RequestParam(value = "syncServer", required = false) String syncServer) {
		PriveAuthForm priveAuthForm = b2BService.readPortfolioNavLatest(5);
		return null;
	}

	@RequestMapping("/readPortfolioBackTestingNavLatest")
	public @ResponseBody List readPortfolioBackTestingNavLatest(
			@RequestParam(value = "etlDate", required = false) String etlDate,
			@RequestParam(value = "syncServer", required = false) String syncServer) {
		PriveAuthForm priveAuthForm = b2BService
				.readPortfolioBackTestingNavLatest(10);
		return null;
	}

	@RequestMapping("/writePortfolioNavToday")
	public @ResponseBody List writePortfolioNavToday(
			@RequestParam(value = "etlDate", required = false) String etlDate,
			@RequestParam(value = "syncServer", required = false) String syncServer) {
		PriveAuthForm priveAuthForm = b2BService.writePortfolioNavToday();
		return null;
	}

}
