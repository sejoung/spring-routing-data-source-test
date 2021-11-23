package com.example.demo.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		DataSourceType dataSourceType = TransactionSynchronizationManager.isCurrentTransactionReadOnly() ?
			DataSourceType.READ_ONLY : DataSourceType.READ_WRITE;
		log.info("### current dataSourceType : {}", dataSourceType.name());
		return dataSourceType;
	}
}

