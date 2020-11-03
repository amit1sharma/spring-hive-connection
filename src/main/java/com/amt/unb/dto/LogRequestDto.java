package com.amt.unb.dto;

public class LogRequestDto {
	
	private String requesterModule;
	private String requestType;
	private String subRequestType;
	private String uniqueIdentifier;
	private String mediaName;
	private String requestTime;
	public String getRequesterModule() {
		return requesterModule;
	}
	public void setRequesterModule(String requesterModule) {
		this.requesterModule = requesterModule;
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
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}
	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	
	

}
