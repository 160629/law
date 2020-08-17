package com.chinatower.product.legalms.common;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;


@Configuration
public class DynamicDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSource firstDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.third")
    public DataSource thirdDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource firstDataSource, DataSource secondDataSource, DataSource thirdDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(CoreConstClass.FIRST, firstDataSource);
        targetDataSources.put(CoreConstClass.SECOND, secondDataSource);
        targetDataSources.put(CoreConstClass.THIRD, thirdDataSource);
        return new DynamicDataSource(firstDataSource, targetDataSources);
    }
}