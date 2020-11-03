package com.amt.unb.entity.phub;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "iccs_audit_log")
public class ICCSAuditLogModel {

	@Id
	@SequenceGenerator(name = "iccs_audit_log_seq", sequenceName = "iccs_audit_log_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iccs_audit_log_seq") 
	private Long requestId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestTimeStamp;
	
	private String requestorModule;
	private String requestType;
	private String subRequestType;
	private String uniqueIdentifier;
	private String mediaName;
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public Date getRequestTimeStamp() {
		return requestTimeStamp;
	}
	public void setRequestTimeStamp(Date requestTimeStamp) {
		this.requestTimeStamp = requestTimeStamp;
	}
	public String getRequestorModule() {
		return requestorModule;
	}
	public void setRequestorModule(String requestorModule) {
		this.requestorModule = requestorModule;
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
