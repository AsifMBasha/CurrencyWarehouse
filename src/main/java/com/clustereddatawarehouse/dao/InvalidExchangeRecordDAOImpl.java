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
import com.clustereddatawarehouse.model.InvalidExchangeRecord;

@Repository("invalidExchangeRecordDao")
public class InvalidExchangeRecordDAOImpl extends AbstractDAO<Integer, InvalidExchangeRecord> implements InvalidExchangeRecordDAO {


	 @Autowired 
	    private SessionFactory sessionFactory;  
	 
	 private static final Logger logger = Logger.getLogger(InvalidExchangeRecordDAOImpl.class);
	
	 public void insertRecordsList(List<InvalidExchangeRecord> exchangeRecordList) throws NonUniqueRecordException {
			
		 logger.debug("insertRecordsList starts");
		 StatelessSession session = sessionFactory.openStatelessSession();
			logger.debug("begin Transaction");
			Transaction tx = session.beginTransaction();
		 try {
				Iterator<InvalidExchangeRecord> iterator = exchangeRecordList.iterator();
				while (iterator.hasNext()) {
					session.insert(iterator.next());
				}
				logger.debug("commit Transaction");
				tx.commit();
				session.close();
		 } catch(ConstraintViolationException  exe){ 
			 tx.rollback();
			 session.close();
			 throw new NonUniqueRecordException("File contains non unique deal id");
			}
			 logger.debug("insertRecordsList ends");
		}
	 
	 public List<InvalidExchangeRecord> findInvalidRecordsByFilename(String filename) {
		 	logger.debug("findInvalidRecordsByFilename starts");
			Criteria criteria = createEntityCriteria();
			logger.debug("searching for invalid records with filename:" + filename);
	        criteria.add(Restrictions.eq("erpk.filename", filename));
	        logger.debug("findInvalidRecordsByFilename ends");
	        return (List<InvalidExchangeRecord>) criteria.list();
		 
		}

}
