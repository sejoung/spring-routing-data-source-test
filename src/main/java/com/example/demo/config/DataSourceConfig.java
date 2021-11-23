package com.example.demo.config;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "product-rds.datasource-read")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties(prefix = "product-rds.datasource-write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @DependsOn({"readDataSource", "writeDataSource"})
    @Bean
    public DataSource routingDataSource(
            @Qualifier("readDataSource") DataSource readDataSource,
            @Qualifier("writeDataSource") DataSource writeDataSource
    ) {
        logDataSourceInfo((HikariDataSource) readDataSource);
        logDataSourceInfo((HikariDataSource) writeDataSource);

        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.READ_ONLY, readDataSource);
        dataSourceMap.put(DataSourceType.READ_WRITE, writeDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(readDataSource);

        return routingDataSource;
    }

    private void logDataSourceInfo(HikariDataSource dataSource) {
        log.info("getJdbcUrl = {}", dataSource.getJdbcUrl());
        log.info("getUsername = {}", dataSource.getUsername());

        try {
            log.info("isReadonly = {}", dataSource.getConnection().isReadOnly());
        } catch (SQLException e) {

        }
    }


    @DependsOn("routingDataSource")
    @Bean
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}

