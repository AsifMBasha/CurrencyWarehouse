package com.clustereddatawarehouse.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clustereddatawarehouse.dao.InvalidExchangeRecordDAO;
import com.clustereddatawarehouse.exception.NonUniqueRecordException;
import com.clustereddatawarehouse.model.ExchangeRecord;
import com.clustereddatawarehouse.model.InvalidExchangeRecord;

@Service("invalidExchangeRecordService")
@Transactional
public class InvalidExchangeRecordServiceImpl implements InvalidExchangeRecordService {
	
	@Autowired
    private InvalidExchangeRecordDAO dao;
	
	 private static final Logger logger = Logger.getLogger(InvalidExchangeRecordServiceImpl.class);

	public void insertRecordsList(List<InvalidExchangeRecord> invalidExchangeRecordList)  throws NonUniqueRecordException{
		 logger.debug("insertRecordsList starts");
		 dao.insertRecordsList(invalidExchangeRecordList);
		 logger.debug("insertRecordsList ends");
	}
	

	 public List<InvalidExchangeRecord> findInvalidRecordsByFilename(String filename) {
		 logger.debug("findInvalidRecordsByFilename starts");
		 logger.debug("findInvalidRecordsByFilename ends");
		 return dao.findInvalidRecordsByFilename(filename);
		 
	 }

}
