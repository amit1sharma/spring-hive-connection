package com.amt.unb.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.amt.unb.dto.RequestDto;
import com.amt.unb.dto.ResponseDto;
import com.amt.unb.dto.body.ResponseBody;
import com.amt.unb.dto.header.ResponseHeader;
import com.amt.unb.exception.HiveCustomException;

import sun.misc.BASE64Encoder;

public class Utility {
	
	public static ResponseDto responseDto(RequestDto reqDto, List<Map<String, Object>> records, boolean status, HiveCustomException e){
		records = records==null? new ArrayList<>():records;
		ResponseDto dto = new ResponseDto();
		dto.setBody(getResponseBody(records));
		dto.setHeader(getResponseHeader(e, reqDto, status, records.size(), 0));
		return dto;
	}
	
	private static ResponseBody getResponseBody(List<Map<String, Object>> records){
		ResponseBody rb = new ResponseBody();
		rb.setRecords(records);
		return rb;
	}
	
	
	private static ResponseHeader getResponseHeader(HiveCustomException e, RequestDto reqDto, boolean status, int recordsCount, int totalRecordsCount){
		ResponseHeader rh = new ResponseHeader();
		rh.setRecordsCount(recordsCount);
		rh.setRequestType(reqDto.getHeader().getRequestType());
		rh.setSubRequestType(reqDto.getHeader().getSubRequestType());
		rh.setResponseTime(new Date().toString());
		rh.setStatus(status);
		if(!status){
			rh.setErrorCode(e.getErrorCode());
			rh.setErrorDesc(e.getErrorDescription());
		}
		rh.setTotalRecordsCount(totalRecordsCount);
		return rh;
	}
	
	public static String addLimitOffset(String query, int pageNo, int numberOfRecords){
		if(!query.startsWith("select") && !query.startsWith("SELECT")){
			return query;
		}
		String tmpQuery = query;
		String[] arr = tmpQuery.split(" ");
		if(arr.length>0){
			
			String[] fromSplit = tmpQuery.split("(?i)from");
			String onlySelects = fromSplit[0].replace(arr[0], "");
			
			pageNo = pageNo<=0?1:pageNo;
			int end = pageNo*numberOfRecords;
			int start = ((pageNo-1)*numberOfRecords)+1;
			tmpQuery = tmpQuery.replace(arr[0], arr[0]+" row_number() over() as rank,");
			tmpQuery = "select "+onlySelects+" from ("+tmpQuery+") q where q.rank between "+start+" and "+end;
		}
		return tmpQuery;
	}
	
	public static String addWhereCondition(String paramName, String paramValue, MapSqlParameterSource paramSource ){
		String condition="";
		if(StringUtils.isNotBlank(paramValue)){
			condition = " and "+paramName+" =:"+paramName+" ";
			paramSource.addValue(paramName, paramValue);
		}
		return condition;
	}
	
	public static String addWhereCondition(String paramName, String paramValue, Map<String, String> paramSource ){
		String condition="";
		if(StringUtils.isNotBlank(paramValue)){
			condition = " and "+paramName+" =:"+paramName+" ";
			paramSource.put(paramName, paramValue);
		}
		return condition;
	}
	
	/**
	 * 
	 * @param webHdfsUrl source path
	 * @param fileName contains name of file to be saved
	 * @param targetPath contains only path
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws HiveCustomException 
	 */
	public static byte[] downloadMime(String webHdfsUrl, String userName, String password) throws ClientProtocolException, IOException, HiveCustomException{
		byte[] result = new byte[]{};
		String enc = userName +":"+ password;
		HttpClient client = HttpClients.createDefault();
      	HttpGet req = new HttpGet(webHdfsUrl);
      	req.addHeader(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString((enc).getBytes()));
      	HttpResponse res = client.execute(req);
      	int status = res.getStatusLine().getStatusCode();
      	System.out.println("Response Status : " + res.getStatusLine().getStatusCode());
      	
      	if(status == 200) {
//      		File file = new File(targetPath);
//      		FileOutputStream outputStream = new FileOutputStream(file);
      		HttpEntity entity = res.getEntity();
      	    ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
      	    entity.writeTo(baos);
//      		outputStream.write(baos.toByteArray());
//      		outputStream.close();
      	    result = baos.toByteArray();
      	} else {
      		result = null;
      		System.out.println(res.getStatusLine().getReasonPhrase());
      		throw new HiveCustomException(2, "hdfs call failed with error code : "+res.getStatusLine().getReasonPhrase());
      	}
      	return result;
	}

}
