package com.clustereddatawarehouse.service;

import java.util.List;

import com.clustereddatawarehouse.exception.NonUniqueRecordException;
import com.clustereddatawarehouse.model.ExchangeRecord;
import com.clustereddatawarehouse.model.InvalidExchangeRecord;

public interface CurrencyService {
	
    void insertRecordsList(List<ExchangeRecord> exchangeRecordList)  throws NonUniqueRecordException;
    public List<ExchangeRecord> findRecordsByFilename(String filename);

}

