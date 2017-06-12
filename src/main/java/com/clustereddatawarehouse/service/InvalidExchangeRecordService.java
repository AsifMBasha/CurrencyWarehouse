package com.clustereddatawarehouse.service;

import java.util.List;

import com.clustereddatawarehouse.exception.NonUniqueRecordException;
import com.clustereddatawarehouse.model.ExchangeRecord;
import com.clustereddatawarehouse.model.InvalidExchangeRecord;

public interface InvalidExchangeRecordService {
	
    void insertRecordsList(List<InvalidExchangeRecord> invalidExchangeRecordList)  throws NonUniqueRecordException;
    public List<InvalidExchangeRecord> findInvalidRecordsByFilename(String filename);

}

