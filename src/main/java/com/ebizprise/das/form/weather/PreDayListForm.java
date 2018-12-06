package com.ebizprise.das.form.weather;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *
 * @author maduar
 * @date 09/08/2018 1:32 PM
 * @email maduar@163.com
 *
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreDayListForm {

    @JSONField(ordinal = 1, serialize = true)
    private String predate;
    @JSONField(name = "HIGH_TEMPERATURE", ordinal = 4, serialize = true)
    private String HIGH_TEMPERATURE;

    @JSONField(name = "LOW_TEMPERATURE", ordinal = 5, serialize = true)
    private String LOW_TEMPERATURE;

    @JSONField(name = "WEATHER_CONDITION", ordinal = 6, serialize = true)
    private String WEATHER_CONDITION;
}
