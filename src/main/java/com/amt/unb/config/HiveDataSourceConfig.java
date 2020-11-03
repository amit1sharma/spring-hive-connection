package com.amt.unb.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.hive.jdbc.HiveDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
public class HiveDataSourceConfig {

	@Value("${hive.connectionURL}")
	private String hiveConnectionURL;

	@Value("${hive.username}")
	private String userName;

	@Value("${hive.password}")
	private String password;
	
	@Value("${hive.jndi.name}")
    private String jndiName;
	
	public Connection getHiveConnection() throws SQLException, ClassNotFoundException{
		Class.forName("org.apache.hive.jdbc.HiveDriver");
		return DriverManager.getConnection(hiveConnectionURL, userName, password);
	}
	
	@Bean("hiveDataSource")
    @Profile({"dev"})
    public DataSource hiveDataSourceDev() {
		org.apache.hive.jdbc.HiveDriver d = new HiveDriver();
		SimpleDriverDataSource datasource = new SimpleDriverDataSource(d, hiveConnectionURL, userName, password);
		return datasource;
    }
    
    @Bean(name = "hiveDataSource")
    @Profile({"uat", "prod"})
	public DataSource hiveDataSourceProd() {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		return dataSourceLookup.getDataSource(jndiName);
		
	}
	
	@Bean(name = "hiveJdbcTemplate")
	public JdbcTemplate hiveJdbcTemplate(@Qualifier("hiveDataSource") DataSource hiveDataSource) throws IOException {
		return new JdbcTemplate(hiveDataSource);
	}
	
	@Bean(name = "hiveJdbcNamedTemplate")
	public NamedParameterJdbcTemplate hiveJdbcNamesTemplate(@Qualifier("hiveDataSource") DataSource hiveDataSource) throws IOException {
		return new NamedParameterJdbcTemplate(hiveDataSource);

	}
	
	
	
}
