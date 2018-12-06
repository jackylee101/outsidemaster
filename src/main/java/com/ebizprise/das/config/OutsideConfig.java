package com.ebizprise.das.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
 *
 * @author maduar
 * @date 07/08/2018 10:48 AM
 * @email maduar@163.com
 *
 * */
@Data
@Component
@NoArgsConstructor
public class OutsideConfig {

    @Value("${code.errCode.weatherErrorCode}")
    private String weatherErrorCode;

    @Value("${code.errCode.cityMappingErrorCode}")
    private String cityMappingErrorCode;

    @Value("${code.errCode.holidayErrorCode}")
    private String holidayErrorCode;

    @Value("${weather.HIGH_TEMPERATURE}")
    private String HIGH_TEMPERATURE;

    @Value("${weather.HIGH_TEMPERATURE}")
    private String LOW_TEMPERATURE;

    @Value("${weather.HIGH_TEMPERATURE}")
    private String WEATHER_CONDITION;

    @Value("${code.mailCode.filecalendarDataCode}")
    private String FILE_CALENDAR_DATACODE;

//    @Value("${defaultPath}")
//    private String DEFAULT_PATH;
}
