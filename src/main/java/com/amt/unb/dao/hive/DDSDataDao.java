package com.amt.unb.dao.hive;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.amt.unb.dto.RequestDto;
import com.amt.unb.exception.HiveCustomException;
import com.amt.unb.utils.Utility;

@Repository
public class DDSDataDao {

	@Autowired
	@Qualifier("hiveJdbcNamedTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${dds.query}")
	private String ddsQuery;
	
	@Value("${insuffucient.param.exception.code}")
	private int insufficientParamExceptionCode;
	
	@Value("${insuffucient.param.exception.desc}")
	private String insufficientParamExceptionDescription;
	
	
	//DDAREFNO = [INPUT] ACCOUNTNUMBER = [INPUT]	CUSTIDNUMBER = [INPUT]	PAYERIDNOWITHORIG = [INPUT]
	public List<Map<String, Object>> getRecords(RequestDto dto) throws HiveCustomException{
		String ddsQuery_tmp = ddsQuery;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		ddsQuery_tmp += Utility.addWhereCondition( "DDAREFNO", dto.getBody().getDdaRefNumber(), paramSource);
		ddsQuery_tmp += Utility.addWhereCondition( "ACCOUNTNUMBER", dto.getBody().getAccountNumber(), paramSource);
		ddsQuery_tmp += Utility.addWhereCondition( "CUSTIDNUMBER", dto.getBody().getCustIdNumber(), paramSource);
		ddsQuery_tmp += Utility.addWhereCondition( "PAYERIDNOWITHORIG", dto.getBody().getPayerIdNumberWithOrig(), paramSource);
		
		if(paramSource==null ||paramSource.getValues().isEmpty()){
			throw new HiveCustomException(insufficientParamExceptionCode, insufficientParamExceptionDescription);
		}
		ddsQuery_tmp = Utility.addLimitOffset(ddsQuery_tmp, dto.getHeader().getPageNo(), dto.getHeader().getNoOfRecordsInPage());
		return namedParameterJdbcTemplate.queryForList(ddsQuery_tmp, paramSource);
	}
}
