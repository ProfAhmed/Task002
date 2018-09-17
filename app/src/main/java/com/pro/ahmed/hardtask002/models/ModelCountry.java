package com.pro.ahmed.hardtask002.models;

public class ModelCountry {
    private ModelCode mCode;
    private String titleEN;
    private String titleAR;
    private String currencyEN;
    private String currencyAR;
    int id;
    int currencyId;
    private String checkLang;

    public ModelCountry(String titleEN, String titleAR, int id, ModelCode mCode, String checkLang) {
        this.titleEN = titleEN;
        this.titleAR = titleAR;
        this.id = id;
        this.mCode = mCode;
        this.checkLang = checkLang;
    }

    public ModelCountry(String titleEN, String titleAR, String checkLang) {
        this.titleEN = titleEN;
        this.titleAR = titleAR;
        this.checkLang = checkLang;
    }


    public String getTitleEN() {
        return titleEN;
    }

    public void setTitleEN(String titleEN) {
        this.titleEN = titleEN;
    }

    public String getTitleAR() {
        return titleAR;
    }

    public void setTitleAR(String titleAR) {
        this.titleAR = titleAR;
    }

    public String getCurrencyEN() {
        return currencyEN;
    }

    public void setCurrencyEN(String currencyEN) {
        this.currencyEN = currencyEN;
    }

    public String getCurrencyAR() {
        return currencyAR;
    }

    public void setCurrencyAR(String currencyAR) {
        this.currencyAR = currencyAR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }


    public ModelCode getmCode() {
        return mCode;
    }

    public void setmCode(ModelCode mCode) {
        this.mCode = mCode;
    }

    @Override
    public String toString() {
        if (checkLang.equals("ara")) {
            return titleAR;
        } else {
            return titleEN;
        }
    }
}

