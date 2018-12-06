package com.ebizprise.das.utilsweb.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 *
 * @author maduar
 * @date 19/07/2018 2:26 PM
 * @email maduar@163.com
 *
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountAndListForm {

    private Long count;
    private List list;

}
