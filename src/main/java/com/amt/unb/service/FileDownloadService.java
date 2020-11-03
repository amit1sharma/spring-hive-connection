package com.amt.unb.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.amt.unb.exception.HiveCustomException;

public interface FileDownloadService {

	byte[] downloadFile(String filePath) throws ClientProtocolException, IOException, HiveCustomException;

}
