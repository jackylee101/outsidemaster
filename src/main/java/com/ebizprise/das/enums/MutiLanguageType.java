package com.ebizprise.das.enums;

public enum MutiLanguageType {

    TW(1, "886", "TW");

    private int index;
    private String phone_code;
    private String nation_code;

    MutiLanguageType(int index, String phone_code, String nation_code) {
        this.index = index;
        this.phone_code = phone_code;
        this.nation_code = nation_code;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public String getNation_code() {
        return nation_code;
    }
}
