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
public class PMSDataDao {

	@Autowired
	@Qualifier("hiveJdbcNamedTemplate")
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${pms.query}")
	private String pmsQuery;
	
	@Value("${insuffucient.param.exception.code}")
	private int insufficientParamExceptionCode;
	
	@Value("${insuffucient.param.exception.desc}")
	private String insufficientParamExceptionDescription;
	
	
	//DDAREFNO = [INPUT] ACCOUNTNUMBER = [INPUT]	CUSTIDNUMBER = [INPUT]	PAYERIDNOWITHORIG = [INPUT]
	public List<Map<String, Object>> getRecords(RequestDto dto) throws HiveCustomException{
		String pmsQuery_tmp = pmsQuery;
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		pmsQuery_tmp += Utility.addWhereCondition( "DocumentID", dto.getBody().getDocumentId(), paramSource);
		pmsQuery_tmp += Utility.addWhereCondition( "Name_En", dto.getBody().getCustomerName(), paramSource);
		pmsQuery_tmp += Utility.addWhereCondition( "ComponentName_En", dto.getBody().getPropertyName(), paramSource);
		
		if(paramSource==null ||paramSource.getValues().isEmpty()){
			throw new HiveCustomException(insufficientParamExceptionCode, insufficientParamExceptionDescription);
		}
		pmsQuery_tmp = Utility.addLimitOffset(pmsQuery_tmp, dto.getHeader().getPageNo(), dto.getHeader().getNoOfRecordsInPage());
		return namedParameterJdbcTemplate.queryForList(pmsQuery_tmp, paramSource);
	}
}
