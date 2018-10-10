package com.danazone.gomgom.admin.bean;

import java.io.Serializable;

public class Information implements Serializable {
    private String id;
    private String phoneNumber;
    private String name;
    private String typeDriver;
    private String registerDay;
    private String state;
    private String idDeal;
    private String latDuelTime;
    private String inDeal;
    private String linkAvatar;
    private String linkDrivingLicense;
    private String linkCar;
    private String address;
    private String licensePlates;
    private String email;
    private String yearOfBirth;
    private String sex;
    private String licenseNumber;
    private String expired;
    private String verified;
    private String identityCard;
    private String linkVehicleCcertificates;

    public Information(){

    }

    public Information(String id, String phoneNumber, String name, String typeDriver, String registerDay, String state, String idDeal, String latDuelTime, String inDeal, String linkAvatar, String linkDrivingLicense, String linkCar, String address, String licensePlates, String email, String yearOfBirth, String sex, String licenseNumber, String expired, String verified, String identityCard, String linkVehicleCcertificates) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.typeDriver = typeDriver;
        this.registerDay = registerDay;
        this.state = state;
        this.idDeal = idDeal;
        this.latDuelTime = latDuelTime;
        this.inDeal = inDeal;
        this.linkAvatar = linkAvatar;
        this.linkDrivingLicense = linkDrivingLicense;
        this.linkCar = linkCar;
        this.address = address;
        this.licensePlates = licensePlates;
        this.email = email;
        this.yearOfBirth = yearOfBirth;
        this.sex = sex;
        this.licenseNumber = licenseNumber;
        this.expired = expired;
        this.verified = verified;
        this.identityCard = identityCard;
        this.linkVehicleCcertificates = linkVehicleCcertificates;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeDriver() {
        return typeDriver;
    }

    public void setTypeDriver(String typeDriver) {
        this.typeDriver = typeDriver;
    }

    public String getRegisterDay() {
        return registerDay;
    }

    public void setRegisterDay(String registerDay) {
        this.registerDay = registerDay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIdDeal() {
        return idDeal;
    }

    public void setIdDeal(String idDeal) {
        this.idDeal = idDeal;
    }

    public String getLatDuelTime() {
        return latDuelTime;
    }

    public void setLatDuelTime(String latDuelTime) {
        this.latDuelTime = latDuelTime;
    }

    public String getInDeal() {
        return inDeal;
    }

    public void setInDeal(String inDeal) {
        this.inDeal = inDeal;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }

    public String getLinkDrivingLicense() {
        return linkDrivingLicense;
    }

    public void setLinkDrivingLicense(String linkDrivingLicense) {
        this.linkDrivingLicense = linkDrivingLicense;
    }

    public String getLinkCar() {
        return linkCar;
    }

    public void setLinkCar(String linkCar) {
        this.linkCar = linkCar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicensePlates() {
        return licensePlates;
    }

    public void setLicensePlates(String licensePlates) {
        this.licensePlates = licensePlates;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getLinkVehicleCcertificates() {
        return linkVehicleCcertificates;
    }

    public void setLinkVehicleCcertificates(String linkVehicleCcertificates) {
        this.linkVehicleCcertificates = linkVehicleCcertificates;
    }
}
