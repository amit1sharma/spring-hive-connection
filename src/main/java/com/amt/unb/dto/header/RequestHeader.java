package com.amt.unb.dto.header;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RequestHeader {
	
    private String requestType;
	
	
    private String userId;
	
	
    private int pageNo;
	
	
    private int noOfRecordsInPage;
	
	
    private String profile;
	
	
    private String subRequestType;

    
    public String getRequestType() {
        return requestType;
    }
    
//    @JsonProperty("RequestType")
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUserId() {
        return userId;
    }

//    @JsonProperty("UserID")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getNoOfRecordsInPage() {
		return noOfRecordsInPage;
	}

	public void setNoOfRecordsInPage(int noOfRecordsInPage) {
		this.noOfRecordsInPage = noOfRecordsInPage;
	}

	public String getProfile() {
        return profile;
    }

//    @JsonProperty("Profile")
    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getSubRequestType() {
        return subRequestType;
    }

//    @JsonProperty("SubRequestType")
    public void setSubRequestType(String subRequestType) {
        this.subRequestType = subRequestType;
    }
}
