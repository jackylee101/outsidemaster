package com.ebizprise.das.form.weather;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *
 * @author maduar
 * @date 07/08/2018 5:16 PM
 * @email maduar@163.com
 *
 * */

public class ApiWeatherListForm {

    @JSONField(ordinal = 1, serialize = true)
    private String cityId;

    @JSONField(ordinal = 2, serialize = true)
    private String cityName;

    @JSONField(ordinal = 3, serialize = true)
    private String month;

    @JSONField(name = "HIGH_TEMPERATURE", ordinal = 4, serialize = true)
    private String HIGH_TEMPERATURE;

    @JSONField(name = "LOW_TEMPERATURE", ordinal = 5, serialize = true)
    private String LOW_TEMPERATURE;

    @JSONField(name = "WEATHER_CONDITION", ordinal = 6, serialize = true)
    private String WEATHER_CONDITION;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getHIGH_TEMPERATURE() {
        return HIGH_TEMPERATURE;
    }

    public void setHIGH_TEMPERATURE(String HIGH_TEMPERATURE) {
        this.HIGH_TEMPERATURE = HIGH_TEMPERATURE;
    }

    public String getLOW_TEMPERATURE() {
        return LOW_TEMPERATURE;
    }

    public void setLOW_TEMPERATURE(String LOW_TEMPERATURE) {
        this.LOW_TEMPERATURE = LOW_TEMPERATURE;
    }

    public String getWEATHER_CONDITION() {
        return WEATHER_CONDITION;
    }

    public void setWEATHER_CONDITION(String WEATHER_CONDITION) {
        this.WEATHER_CONDITION = WEATHER_CONDITION;
    }

    @Override
    public String toString() {
        return "ApiWeatherListForm{" +
                "cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", month='" + month + '\'' +
                ", HIGH_TEMPERATURE='" + HIGH_TEMPERATURE + '\'' +
                ", LOW_TEMPERATURE='" + LOW_TEMPERATURE + '\'' +
                ", WEATHER_CONDITION='" + WEATHER_CONDITION + '\'' +
                '}';
    }
}
