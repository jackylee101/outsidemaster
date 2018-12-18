package com.ebizprise.das.scheduled.service.weather;

import java.util.List;
import java.util.Map;

/*
 *
 * @author Jacky
 * @date 09/11/2018 10:35 AM
 * @email jacky.lee@ebizprise.com
 *
 * */
public interface WeatherSync {

	void takeWeatherDate2DB();

	void takeWeatherDate2DB(String etlDate, String env);

	List takeWeatherDate2DBtest(String syncServer);

}
