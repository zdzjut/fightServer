package com.zd.fight.controller;

public class TestBean {


    private String receiptNumber;
    private String picked;
    private String usableForeign;
    private String exchange;
    private String usableRmb;
    private String remark;

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getPicked() {
        return picked;
    }

    public void setPicked(String picked) {
        this.picked = picked;
    }

    public String getUsableForeign() {
        return usableForeign;
    }

    public void setUsableForeign(String usableForeign) {
        this.usableForeign = usableForeign;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getUsableRmb() {
        return usableRmb;
    }

    public void setUsableRmb(String usableRmb) {
        this.usableRmb = usableRmb;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "receiptNumber='" + receiptNumber + '\'' +
                ", picked='" + picked + '\'' +
                ", usableForeign='" + usableForeign + '\'' +
                ", exchange='" + exchange + '\'' +
                ", usableRmb='" + usableRmb + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
