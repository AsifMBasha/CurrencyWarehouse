
package com.clustereddatawarehouse.dao;

import java.util.List;

import com.clustereddatawarehouse.exception.NonUniqueRecordException;
import com.clustereddatawarehouse.model.ExchangeRecord;


public interface CurrencyDAO {

    void insertRecordsList(List<ExchangeRecord> exchangeRecordList) throws NonUniqueRecordException;
    public List<ExchangeRecord> findRecordsByFilename(String filename);
  
}

