package com.amt.unb.dto.header;

public class ResponseHeader {
	
	private String responseTime;
	private int totalRecordsCount;
	private int recordsCount;
	private String requestType;
	private String subRequestType;
	private boolean status;
	private Integer errorCode;
	private String errorDesc;
	
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}
	public int getRecordsCount() {
		return recordsCount;
	}
	public void setRecordsCount(int recordsCount) {
		this.recordsCount = recordsCount;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getSubRequestType() {
		return subRequestType;
	}
	public void setSubRequestType(String subRequestType) {
		this.subRequestType = subRequestType;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

}
