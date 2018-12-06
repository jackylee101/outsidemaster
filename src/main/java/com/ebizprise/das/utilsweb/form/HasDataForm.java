package com.ebizprise.das.utilsweb.form;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/*
 *
 * @author maduar
 * @date 01/08/2018 4:35 PM
 * @email maduar@163.com
 *
 * */
public class HasDataForm extends NoDataForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @JSONField(ordinal = 4)
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
