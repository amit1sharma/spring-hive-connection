package com.amt.unb.service;

import com.amt.unb.dto.RequestDto;
import com.amt.unb.exception.HiveCustomException;

import java.util.List;
import java.util.Map;

public interface DataService {
    public List<Map<String, Object>> getRecords(RequestDto requestDto) throws HiveCustomException;
}
