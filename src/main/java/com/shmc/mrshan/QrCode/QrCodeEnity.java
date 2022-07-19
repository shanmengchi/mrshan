package com.shmc.mrshan.QrCode;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class QrCodeEnity {
    @Excel(name="qrcode" , width = 20)
    private String qrcode;

    @Excel(name="deptId" , width = 20)
    private String deptId;

    @Excel(name="remark" , width = 20)
    private String remark;

    @Excel(name="deviceid" , width = 20)
    private String deviceid;

    @Excel(name="devicetype" , width = 20)
    private String devicetype;

    @Excel(name="riskType" , width = 20)
    private String riskType;

    @Excel(name="DEPTNAME" , width = 20)
    private String deptname;

    @Excel(name="devicename" , width = 20)
    private String devicename;

    @Excel(name="position" , width = 20)
    private String position;

    @Excel(name="responsible_org" , width = 20)
    private String responsible_org;

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getResponsible_org() {
        return responsible_org;
    }

    public void setResponsible_org(String responsible_org) {
        this.responsible_org = responsible_org;
    }
}
