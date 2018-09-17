package com.pro.ahmed.hardtask002.models;

public class ModelRegistration {
    private String fullName;
    private String password;
    private String codeName;
    private String emailAddress;
    private String countryName;
    private String cityName;
    private static int id = 0;
    private int code;


    public ModelRegistration(String fullName, String password, String codeName, String emailAddress, String countryName, String cityName, int code) {
        this.fullName = fullName;
        this.password = password;
        this.codeName = codeName;
        this.emailAddress = emailAddress;
        this.countryName = countryName;
        this.cityName = cityName;
        this.code = code;
        id++;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", codeName='" + codeName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", countryName='" + countryName + '\'' +
                ", CityName='" + cityName + '\'' +
                ", code=" + code +
                ", id=" + id +
                '}';
    }
}
