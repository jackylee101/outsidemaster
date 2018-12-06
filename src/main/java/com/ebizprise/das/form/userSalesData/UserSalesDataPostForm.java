package com.ebizprise.das.form.userSalesData;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

/*
 *
 * @author maduar
 * @date 09/07/2018 10:26 AM
 * @email maduar@163.com
 *
 * */
public class UserSalesDataPostForm implements Serializable{
    private String custId;
    private String[] title;
    private String[][] data;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }

    public UserSalesDataPostForm() {
    }
}
