package com.clustereddatawarehouse.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clustereddatawarehouse.dao.CurrencyDAO;
import com.clustereddatawarehouse.exception.NonUniqueRecordException;
import com.clustereddatawarehouse.model.ExchangeRecord;

@Service("currencyService")
@Transactional
public class CurrencyServiceImpl implements CurrencyService {
	
	@Autowired
    private CurrencyDAO dao;
	
	 private static final Logger logger = Logger.getLogger(CurrencyServiceImpl.class);

	 public void insertRecordsList(List<ExchangeRecord> exchangeRecordList) throws NonUniqueRecordException{
		 logger.debug("insertRecordsList starts");
		 dao.insertRecordsList(exchangeRecordList);
		 logger.debug("insertRecordsList ends");
	 }
	
	 public List<ExchangeRecord> findRecordsByFilename(String filename) {
		 logger.debug("findRecordsByFilename starts");
		 logger.debug("findRecordsByFilename ends");
		 return dao.findRecordsByFilename(filename);
	 }

}
