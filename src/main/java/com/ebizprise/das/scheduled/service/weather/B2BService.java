package com.ebizprise.das.scheduled.service.weather;

import com.ebizprise.das.form.base.PriveAuthForm;

/*
 *
 * @author Jacky
 * @date 09/11/2018 10:35 AM
 * @email jacky.lee@ebizprise.com
 *
 * */
public interface B2BService {

	PriveAuthForm readPortfolioNavLatest(int num);

	PriveAuthForm readPortfolioBackTestingNavLatest(int num);
	
	PriveAuthForm writePortfolioNavToday() ;

}
