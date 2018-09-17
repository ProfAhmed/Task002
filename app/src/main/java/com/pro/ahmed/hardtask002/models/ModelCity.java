package com.pro.ahmed.hardtask002.models;

public class ModelCity {
    private String cityEN;
    private String cityAR;
    private int id;
    private String checkLang;

    public ModelCity(String cityEN, String cityAR, String checkLang) {

        this.cityEN = cityEN;
        this.cityAR = cityAR;
        this.checkLang = checkLang;
    }

    public String getCityEN() {
        return cityEN;
    }

    public void setCityEN(String cityEN) {
        this.cityEN = cityEN;
    }

    public String getCityAR() {
        return cityAR;
    }

    public void setCityAR(String cityAR) {
        this.cityAR = cityAR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCheckLang() {
        return checkLang;
    }

    public void setCheckLang(String checkLang) {
        this.checkLang = checkLang;
    }

    @Override
    public String toString() {
        if (checkLang.equals("ara")) {
            return cityAR;
        } else {
            return cityEN;
        }
    }
}
