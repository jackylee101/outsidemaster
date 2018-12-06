package com.ebizprise.das.form.userGrant;

/*
 *
 * @author maduar
 * @date 18/07/2018 5:51 PM
 * @email maduar@163.com
 *
 * */
public class UseApiGrantForm {
    private String custId;

    public UseApiGrantForm(String custId) {
        this.custId = custId;
    }

    public String getCustId() {

        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public UseApiGrantForm() {

    }
}
