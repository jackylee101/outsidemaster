package com.ebizprise.das.form.system;

/*
 *
 * @author maduar
 * @date 18/07/2018 11:22 PM
 * @email maduar@163.com
 *
 * */
public class QueryDataForm extends StatusForm {
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Object data;

}
