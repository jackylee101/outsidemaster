package com.ebizprise.das.utilsweb.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/*
 *
 * @author maduar
 * @date 09/08/2018 5:31 PM
 * @email maduar@163.com
 *
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountAndMapForm {
    private Long count;
    private Map map;
}
