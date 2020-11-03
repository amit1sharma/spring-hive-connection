package com.amt.unb.service.impl;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amt.unb.exception.HiveCustomException;
import com.amt.unb.service.FileDownloadService;
import com.amt.unb.utils.Utility;

@Service
public class FileDownloadServiceImpl implements FileDownloadService {
	
	@Value("${hive.username}")
	private String userName;
	
	@Value("${hive.password}")
	private String password;
	
	@Value("${hdfs.web.domain.url}")
	private String hdfsWebDomain;
	
	@Override
	public byte[] downloadFile(String filePath) throws ClientProtocolException, IOException, HiveCustomException{
		String fullPath = hdfsWebDomain+""+filePath+"?op=OPEN";
		return Utility.downloadMime(fullPath, userName, password);
    }

}
