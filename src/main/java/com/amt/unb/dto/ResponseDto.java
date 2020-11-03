package com.amt.unb.dto;

import com.amt.unb.dto.body.ResponseBody;
import com.amt.unb.dto.header.ResponseHeader;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ResponseDto {
	private ResponseHeader header;
	
	private ResponseBody body;
	public ResponseHeader getHeader() {
		return header;
	}
	public void setHeader(ResponseHeader header) {
		this.header = header;
	}
	public ResponseBody getBody() {
		return body;
	}
	public void setBody(ResponseBody body) {
		this.body = body;
	}
	
}
