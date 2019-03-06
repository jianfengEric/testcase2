package com.gea.portal.eny.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dong on 2018/9/10.
 */
public class MoneyPoolDto implements Serializable {

    private String moneyPoolId;
    private String participantId;
    private String geaMoneyTransferId;
    private String balanceUpdateTime;
    private BigDecimal beforeTotalBalance;
    private BigDecimal afterTotalBalance;
    private String transactionType;
    private String transactionDateTime;
    private String currency;
    private BigDecimal amount;
    private String serviceId;
    private String remark;
    private String instance;
    
    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private Boolean isAscending;

    private String refNo;

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getGeaMoneyTransferId() {
        return geaMoneyTransferId;
    }

    public void setGeaMoneyTransferId(String geaMoneyTransferId) {
        this.geaMoneyTransferId = geaMoneyTransferId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMoneyPoolId() {
        return moneyPoolId;
    }

    public void setMoneyPoolId(String moneyPoolId) {
        this.moneyPoolId = moneyPoolId;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getBalanceUpdateTime() {
        return balanceUpdateTime;
    }

    public void setBalanceUpdateTime(String balanceUpdateTime) {
        this.balanceUpdateTime = balanceUpdateTime;
    }

    public BigDecimal getBeforeTotalBalance() {
        return beforeTotalBalance;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public void setBeforeTotalBalance(BigDecimal beforeTotalBalance) {
        this.beforeTotalBalance = beforeTotalBalance;
    }

    public BigDecimal getAfterTotalBalance() {
        return afterTotalBalance;
    }

    public void setAfterTotalBalance(BigDecimal afterTotalBalance) {
        this.afterTotalBalance = afterTotalBalance;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public Boolean getIsAscending() {
		return isAscending;
	}

	public void setIsAscending(Boolean isAscending) {
		this.isAscending = isAscending;
	}
}
