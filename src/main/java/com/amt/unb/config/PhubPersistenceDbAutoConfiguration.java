package com.amt.unb.config;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
		basePackages="com.amt.unb.dao.phub",
  entityManagerFactoryRef = "phubEntityManagerFactory",
  transactionManagerRef = "phubzoneTransactionManager")
public class PhubPersistenceDbAutoConfiguration {
	@Value("${spring.phub-datasource.jndi-name}")
    private String jndiName;
	
	@Primary
    @Bean("phubEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean phubEntityManager(EntityManagerFactoryBuilder builder, @Qualifier ("phubDataSource")DataSource dataSource) {
		return builder
				.dataSource(dataSource)
				.persistenceUnit("phub")
				.packages("com.amt.unb.entity.phub")
				.build();
    }
	@Primary
    @Bean
    public PlatformTransactionManager phubTransactionManager(@Qualifier("phubEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    	return new JpaTransactionManager(entityManagerFactory);
    }
    
	@Primary
    @Bean("phubDataSource")
    @Profile({"dev", "test"})
    @ConfigurationProperties(prefix="spring.phub-datasource")
    public DataSource phubDataSource() {
        return DataSourceBuilder.create().build();
    }
    
	@Primary
    @Bean(name = "phubDataSource")
    @Profile({"uat", "prod"})
	public DataSource dataSourceProd() {
		JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
		return dataSourceLookup.getDataSource(jndiName);
		
	}
}
