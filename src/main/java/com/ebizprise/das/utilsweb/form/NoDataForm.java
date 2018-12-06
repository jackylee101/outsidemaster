package com.ebizprise.das.utilsweb.form;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/*
 *
 * @author maduar
 * @date 24/07/2018 1:33 PM
 * @email maduar@163.com
 *
 * */
public class NoDataForm extends BaseResponseForm implements Serializable{
    private static final long serialVersionUID = 1L;

    @JSONField(ordinal = 3)
    private String errCode;

    public static NoDataForm success() {
        return new NoDataForm();
    }

    public static NoDataForm error(String errCode, String errCodeMsg) {
        return new NoDataForm(errCode, errCodeMsg);
    }

    public NoDataForm(String errCode, String errCodeMsg) {
        this.errCode = errCode;
        this.setErrCode(errCodeMsg);
        this.setSuccess(false);
    }

    public NoDataForm() {
        this.errCode = null;
        this.setError(null);
        this.setSuccess(true);
    }

    //    private NoDataForm(String errCode, String errCodeMsg) {
//        this.setSuccess(true);
//        this.setErrCode(null);
//        this.setError(null);
//        this.data = data;
//    }


    public String getErrCode() {
        return errCode;
    }

    @Override
    public String toString() {
        return "NoDataForm{" +
                "errCode='" + errCode + '\'' +
                '}';
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
