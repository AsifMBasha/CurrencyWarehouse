package com.clustereddatawarehouse.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clustereddatawarehouse.exception.NonUniqueRecordException;
import com.clustereddatawarehouse.model.ExchangeRecord;

@Repository("currencyDao")
public class CurrencyDAOImpl extends AbstractDAO<Integer, ExchangeRecord> implements CurrencyDAO {

	 @Autowired 
	    private SessionFactory sessionFactory;
	 
	 private static final Logger logger = Logger.getLogger(CurrencyDAOImpl.class);
	
	public void insertRecordsList(List<ExchangeRecord> exchangeRecordList) throws NonUniqueRecordException{

		logger.debug("insertRecordsList starts");
		StatelessSession session = sessionFactory.openStatelessSession();
		logger.debug("begin Transaction");
		Transaction tx = session.beginTransaction();
		try{
			Iterator<ExchangeRecord> iterator = exchangeRecordList.iterator();
			while (iterator.hasNext()) {
				session.insert(iterator.next());
			}
			logger.debug("Commit Transaction");
			tx.commit();
			session.close();
			
		} catch(ConstraintViolationException  exe){ 
			tx.rollback();
			session.close();
			throw new NonUniqueRecordException("File contains non unique deal id");
		}
		logger.debug("insertRecordsList ends");
		
	} 
	
	 public List<ExchangeRecord> findRecordsByFilename(String filename) {
		 logger.debug("findRecordsByFilename starts");
			Criteria criteria = createEntityCriteria();
			 logger.debug("searching for valid records with filename:" + filename);
	        criteria.add(Restrictions.eq("filename", filename));
	        logger.debug("findRecordsByFilename ends");
	        return (List<ExchangeRecord>) criteria.list();
		 
		}

}
