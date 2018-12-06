package com.ebizprise.das.form.weather;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/*
 *
 * @author maduar
 * @date 09/08/2018 1:30 PM
 * @email maduar@163.com
 *
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CusWeatherListTempForm implements Serializable{

    private static final long serializableUID = 1L;

    @JSONField(ordinal = 1, serialize = true)
    private String cityId;

    @JSONField(ordinal = 2, serialize = true)
    private String cityName;

    @JSONField(ordinal = 3, serialize = true)
    private String currentdate;

    @JSONField(name = "HIGH_TEMPERATURE", ordinal = 4, serialize = true)
    private String HIGH_TEMPERATURE;

    @JSONField(name = "LOW_TEMPERATURE", ordinal = 5, serialize = true)
    private String LOW_TEMPERATURE;

    @JSONField(name = "WEATHER_CONDITION", ordinal = 6, serialize = true)
    private String WEATHER_CONDITION;

    @JSONField(ordinal = 7, serialize = true)
    private List<PreDayListForm> preDayList;

}
