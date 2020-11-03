package com.amt.unb.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amt.unb.dto.LogRequestDto;
import com.amt.unb.dto.RequestDto;
import com.amt.unb.dto.ResponseDto;
import com.amt.unb.exception.HiveCustomException;
import com.amt.unb.facade.HiveDataServiceFacade;
import com.amt.unb.utils.Utility;

@Controller
public class HiveController {
	
	@RequestMapping("/")
	@ResponseBody
	public String getStatus(){
		return "application is up";
	}

	@Autowired
	private HiveDataServiceFacade hiveDataServiceFacade;

	@RequestMapping(value = "/get", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> getRecords(@RequestBody RequestDto request) {
		long startTime = System.currentTimeMillis();
		ResponseDto responseDto = new ResponseDto();
		try {
			List<Map<String, Object>> list = hiveDataServiceFacade.getData(request);
			responseDto = Utility.responseDto(request, list, true, null);
		} catch(HiveCustomException e){
			e.printStackTrace();
			responseDto = Utility.responseDto(request, null, false, e);
		} catch (Exception e){
			e.printStackTrace();
			HiveCustomException ce = new HiveCustomException(10, e.getMessage());
			responseDto = Utility.responseDto(request, null, false, ce);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("time taken : "+((endTime-startTime)/1000));
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
	}
	
	@RequestMapping(value="/log/save", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity saveLog(LogRequestDto dto) {
		try {
			hiveDataServiceFacade.saveLog(dto);
		} catch (HiveCustomException e) {
			e.printStackTrace();
//			responseDto = Utility.responseDto(request, null, false, e);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@RequestMapping(value="/download", method=RequestMethod.GET, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public HttpEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName ){
//		String webHdfsUrl = "https://adcu9500.adcbmis.local:8443/gateway/adcbuat_cliservice/webhdfs/v1/xUNB_uat/tmp/DDS/21200000332016000121130/21200000332016000121130.txt/?op=OPEN";
		try {
			byte[] bytes = hiveDataServiceFacade.downloadFile(fileName);
			HttpHeaders header = new HttpHeaders();
//		    header.setContentType(MediaType.APPLICATION_PDF);
		    header.set(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="  + fileName.replace(" ", "_"));
		    header.setContentLength(bytes.length);
		    return new HttpEntity<byte[]>(bytes, header);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (HiveCustomException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
