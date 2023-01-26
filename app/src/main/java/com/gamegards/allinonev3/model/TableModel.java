package com.gamegards.allinonev3.model;

public class TableModel {

    private String id;
    private String bootValue;
    private String maximumBlind;
    private String chaalLimit;
    private String potLimit;
    private String addedDate;
    private String updatedDate;
    private String isDeleted;
    private String onlineMembers;
    private String point_value = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBootValue() {
        return bootValue;
    }

    public void setBootValue(String bootValue) {
        this.bootValue = bootValue;
    }

    public String getMaximumBlind() {
        return maximumBlind;
    }

    public void setMaximumBlind(String maximumBlind) {
        this.maximumBlind = maximumBlind;
    }

    public String getChaalLimit() {
        return chaalLimit;
    }

    public void setChaalLimit(String chaalLimit) {
        this.chaalLimit = chaalLimit;
    }

    public String getPotLimit() {
        return potLimit;
    }

    public void setPotLimit(String potLimit) {
        this.potLimit = potLimit;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getOnlineMembers() {
        return onlineMembers;
    }

    public void setOnlineMembers(String onlineMembers) {
        this.onlineMembers = onlineMembers;
    }

    public String getPoint_value() {
        return point_value;
    }

    public void setPoint_value(String point_value) {
        this.point_value = point_value;
    }
}
