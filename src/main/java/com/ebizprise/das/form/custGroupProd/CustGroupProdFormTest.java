package com.ebizprise.das.form.custGroupProd;

import java.math.BigDecimal;

/*
 *
 * @author maduar
 * @date 17/07/2018 4:52 PM
 * @email maduar@163.com
 *
 * */
public class CustGroupProdFormTest {
    private String cmdDate;
    private String custId;
    private String orgStoreId;
    private String ageDescript;
    private String genderDescript;
    private String itemName;
    private Integer itemRank;

    public CustGroupProdFormTest(Object[] columns) {
        this.cmdDate = (String) columns[0];
        this.custId = (String) columns[1];
        this.orgStoreId = (String) columns[2];
        this.ageDescript = (String) columns[3];
        this.genderDescript = (String) columns[4];
        this.itemName = (String) columns[5];
        this.itemRank = (columns[6] != null)?((BigDecimal)columns[6]).intValue():0;
    }

    public String getCmdDate() {
        return cmdDate;
    }

    public void setCmdDate(String cmdDate) {
        this.cmdDate = cmdDate;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getOrgStoreId() {
        return orgStoreId;
    }

    public void setOrgStoreId(String orgStoreId) {
        this.orgStoreId = orgStoreId;
    }

    public String getAgeDescript() {
        return ageDescript;
    }

    public void setAgeDescript(String ageDescript) {
        this.ageDescript = ageDescript;
    }

    public String getGenderDescript() {
        return genderDescript;
    }

    public void setGenderDescript(String genderDescript) {
        this.genderDescript = genderDescript;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemRank() {
        return itemRank;
    }

    public void setItemRank(Integer itemRank) {
        this.itemRank = itemRank;
    }
}
