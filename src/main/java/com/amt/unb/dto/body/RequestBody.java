package com.amt.unb.dto.body;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RequestBody {
	
    private String businessDate;
    private String itemDin;
    private String creditAccount;
    private String amount;
    private String chequeNumber;
    private String routeNumber;
    private String depositAccountId;
    private String newChequeAccount;
    private String newChequeNumber;
    private String newChequeRT;
    private String payerIban;
    private String drawAccount;
    
    private String ddaRefNumber;
    private String accountNumber;
    private String custIdNumber;
    private String payerIdNumberWithOrig;
    
    private String documentId;
    private String customerName;
    private String propertyName;
    
    
    private String terminalId;
    private String fileDate;
    
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getItemDin() {
		return itemDin;
	}
	public void setItemDin(String itemDin) {
		this.itemDin = itemDin;
	}
	public String getCreditAccount() {
		return creditAccount;
	}
	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getChequeNumber() {
		return chequeNumber;
	}
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	public String getRouteNumber() {
		return routeNumber;
	}
	public void setRouteNumber(String routeNumber) {
		this.routeNumber = routeNumber;
	}
	public String getDepositAccountId() {
		return depositAccountId;
	}
	public void setDepositAccountId(String depositAccountId) {
		this.depositAccountId = depositAccountId;
	}
	public String getNewChequeAccount() {
		return newChequeAccount;
	}
	public void setNewChequeAccount(String newChequeAccount) {
		this.newChequeAccount = newChequeAccount;
	}
	public String getNewChequeNumber() {
		return newChequeNumber;
	}
	public void setNewChequeNumber(String newChequeNumber) {
		this.newChequeNumber = newChequeNumber;
	}
	public String getNewChequeRT() {
		return newChequeRT;
	}
	public void setNewChequeRT(String newChequeRT) {
		this.newChequeRT = newChequeRT;
	}
	public String getPayerIban() {
		return payerIban;
	}
	public void setPayerIban(String payerIban) {
		this.payerIban = payerIban;
	}
	public String getDrawAccount() {
		return drawAccount;
	}
	public void setDrawAccount(String drawAccount) {
		this.drawAccount = drawAccount;
	}
	public String getDdaRefNumber() {
		return ddaRefNumber;
	}
	public void setDdaRefNumber(String ddaRefNumber) {
		this.ddaRefNumber = ddaRefNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCustIdNumber() {
		return custIdNumber;
	}
	public void setCustIdNumber(String custIdNumber) {
		this.custIdNumber = custIdNumber;
	}
	public String getPayerIdNumberWithOrig() {
		return payerIdNumberWithOrig;
	}
	public void setPayerIdNumberWithOrig(String payerIdNumberWithOrig) {
		this.payerIdNumberWithOrig = payerIdNumberWithOrig;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	
}
