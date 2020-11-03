package com.amt.unb.dto;

import com.amt.unb.dto.body.RequestBody;
import com.amt.unb.dto.header.RequestHeader;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RequestDto {
	
//	@JsonProperty("Header")
    private RequestHeader header;
	
//	@JsonProperty("Body")
    private RequestBody body;

	public RequestHeader getHeader() {
		return header;
	}

	public void setHeader(RequestHeader header) {
		this.header = header;
	}

	public RequestBody getBody() {
		return body;
	}

	public void setBody(RequestBody body) {
		this.body = body;
	}
    
    

}
