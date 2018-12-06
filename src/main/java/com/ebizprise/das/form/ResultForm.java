package com.ebizprise.das.form;

import com.ebizprise.das.form.system.StatusForm;

import java.io.Serializable;

/*
 *
 * @author maduar
 * @date 24/07/2018 1:33 PM
 * @email maduar@163.com
 *
 * */
public class ResultForm extends StatusForm implements Serializable{
    private static final long serialVersionUID = 1L;

    private String errCode;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
