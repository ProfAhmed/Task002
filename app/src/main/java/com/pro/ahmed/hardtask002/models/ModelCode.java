package com.pro.ahmed.hardtask002.models;

public class ModelCode {
    private String codeEN;
    private String codeAR;
    private int code;
    private String checkLang;

    public ModelCode(String codeEN, String codeAR, int code, String checkLang) {

        this.codeEN = codeEN;
        this.codeAR = codeAR;
        this.code = code;
        this.checkLang = checkLang;
    }

    public ModelCode(String codeEN, String codeAR, String checkLang) {

        this.codeEN = codeEN;
        this.codeAR = codeAR;
        this.code = code;
        this.checkLang = checkLang;
    }

    public String getCodeEN() {
        return codeEN;
    }

    public void setCodeEN(String codeEN) {
        this.codeEN = codeEN;
    }

    public String getCodeAR() {
        return codeAR;
    }

    public void setCodeAR(String codeAR) {
        codeAR = codeAR;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        if (checkLang.equals("ara")) {
            return codeAR;
        } else {
            return codeEN;
        }
    }
}


