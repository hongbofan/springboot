package com.example.entity;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountInfo implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    private String shopId;

    private Short status;

    private BigDecimal totalAmount;

    private BigDecimal balanceAmount;

    private BigDecimal backMoney;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public BigDecimal getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(BigDecimal backMoney) {
        this.backMoney = backMoney;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}