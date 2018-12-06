package com.ebizprise.das.utilsweb.form.jwt;

import java.io.Serializable;

/*
 *
 * @author maduar
 * @date 09/07/2018 11:48 AM
 * @email maduar@163.com
 *
 * */
public class TokenKeyForm implements Serializable {

    private static final long serialVersionUID = 1L;
    private String custId;
    private String tokenKey;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public TokenKeyForm(String custId, String tokenKey) {

        this.custId = custId;
        this.tokenKey = tokenKey;
    }

    public TokenKeyForm() {
    }

    @Override
    public String toString() {
        return "TokenKeyForm{" +
                "custId='" + custId + '\'' +
                ", tokenKey='" + tokenKey + '\'' +
                '}';
    }
}
