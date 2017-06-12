package com.clustereddatawarehouse.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.clustereddatawarehouse.exception.NonUniqueRecordException;
import com.clustereddatawarehouse.model.ExchangeRecord;
import com.clustereddatawarehouse.model.InvalidExchangeRecord;
import com.clustereddatawarehouse.model.InvalidExchangeRecordPK;
import com.clustereddatawarehouse.model.RecordStatus;
import com.clustereddatawarehouse.service.CurrencyService;
import com.clustereddatawarehouse.service.InvalidExchangeRecordService;

@Controller
@ComponentScan("com.clustereddatawarehouse") 
public class UploadController {
	
	
	@Autowired
	CurrencyService cservice;
	
	@Autowired
	InvalidExchangeRecordService ixrservice;
	 
	 @Autowired
	 MessageSource messageSource;

	 private static final Logger logger = Logger.getLogger(UploadController.class);
  
    @RequestMapping(value = { "/uploadScreen" }, method = RequestMethod.GET)
    public String uploadScreen() {
    	logger.debug("uploadScreen called");
        return "upload";
    }
    
    @RequestMapping(value = { "/findRecords" }, method = RequestMethod.GET)
    public String findRecordsFunction() {
    	logger.debug("findRecordsFunction called");
        return "findRecords";
    }
    
    @RequestMapping(value = { "/home","/" }, method = RequestMethod.GET)
    public String goHome() {
    	logger.debug("goHome called");
        return "index";
    }
    
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file,
                                     ModelMap model) {

    	logger.debug("uploadFile starts");  
    	
    	 String filename = file.getOriginalFilename();
    	 
    	 //System should not import same file twice.   so check is file already imported before.	 
         List<ExchangeRecord>  records =  cservice.findRecordsByFilename(filename);    	
         List<InvalidExchangeRecord>  invalidrecords =  ixrservice.findInvalidRecordsByFilename(filename);
         if(records.size() == 0 && invalidrecords.size() == 0 ){  
        	 
        	 model.addAttribute("duplicateFile", false);
         	//Start timer
         	 Calendar cal = Calendar.getInstance();
              SimpleDateFormat sdfmt = new SimpleDateFormat("HH:mm:ss");
         	 logger.info("Start time for file : "+filename +":" + sdfmt.format(cal.getTime()) );
         	 
         	 List<RecordStatus> recordstatusList = new ArrayList<RecordStatus>();
             try {
             	 // start working on the file
                 InputStream inputStream = file.getInputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                 List<ExchangeRecord> exchangeRecordList = new ArrayList<ExchangeRecord>();
                 List<InvalidExchangeRecord> invalidExchangeRecordList = new ArrayList<InvalidExchangeRecord>();
                
            		
                 
                
                 String line;
                 int i = 1;
                 while ((line = bufferedReader.readLine()) != null)
                 {
                 	logger.debug("record:"+ line);
                 	RecordStatus rs = new RecordStatus();
                 	rs.setRecordData(line);
                 		boolean isRecordValid = true;
                 		ExchangeRecord e = new ExchangeRecord();   
     	                // use comma as separator
     	                String[] strs = line.split(",");
     	                if(strs.length == 5){
     	                	if(strs[1].length() ==3 && strs[2].length() ==3){
     	                		
     	                		Date date = null;
     	                		try {
     	                		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     	                		    date = sdf.parse(strs[3]);
     	                		    if (!strs[3].equals(sdf.format(date))) {
     	                		        date = null;
     	                		    }
     	                		} catch (ParseException ex) {
     	                			logger.error("Date not in proper format. Expected format:yyyy-MM-dd HH:mm:ss");
     	                		}
     	                		if(date != null) {

     	                			String amtText = strs[4];
     	                			BigDecimal amt = new BigDecimal(amtText);
     	                			amt = amt.setScale(2,BigDecimal.ROUND_HALF_UP);
     	                			 
     	                			if(!(amt.precision()>8))
     	                			{
     	                				
     	                				//Valid record
     	                				logger.debug("Valid record");
     	                				isRecordValid = true;
     	                				e.setDealId(strs[0]);
     	                				e.setFilename(filename);
     	             	                e.setFromcurrency(strs[1]);
     	             	                e.setTocurrency(strs[2]);
     	             	                e.setDealtime(date);
     	             	                e.setDealamount(amt);
     	             	                exchangeRecordList.add(e);
     	             	                rs.setStatus("VALID");
     	             	                
     	                				
     	                			} else { 
     		                				logger.error("Invalid record: Amount not correct");
     		                				isRecordValid = false; 
     	                				}
     	                			   
     	                		} else { 
     	                				logger.error("Invalid record: Date not correct"); 
     	                				isRecordValid = false;
     	                				}
     	                	} else { 
     		                		logger.error("Invalid record:From Currency or TO Currenc not correct");
     		                		isRecordValid = false; 
     	                		}
     	                }else { 
     	                		logger.error("Invalid record:structure not currect");
     	                		isRecordValid = false; 
     	                	}
     	                
     	                if(!isRecordValid) { 
     	                	
     	                	InvalidExchangeRecordPK erpk = new InvalidExchangeRecordPK(i,filename);
     	            		InvalidExchangeRecord ie = new InvalidExchangeRecord();
     		                ie.setErpk(erpk);
     		                ie.setRecordtext(line);
     		                invalidExchangeRecordList.add(ie);
     		                rs.setStatus("INVALID");
     	                }
     	               
                 	   recordstatusList.add(rs);
                 	   i++;
                  }

                 cservice.insertRecordsList(exchangeRecordList);
                 ixrservice.insertRecordsList(invalidExchangeRecordList);
                 model.addAttribute("filename", filename);
                    
                 } catch(NonUniqueRecordException  exe){
            		 logger.error("Deal Not unique");
            		 model.addAttribute("duplicatedeal", true);
            		
                 }
             
             	catch(Exception ex){
          		 logger.error("Error occured: " + ex.getMessage());
          		logger.error("Error occured: " + ex.getStackTrace());
          		  
             	}

             model.addAttribute("recordstatusList", recordstatusList);
             cal = Calendar.getInstance();
             logger.info("End time for file : "+filename +":" + sdfmt.format(cal.getTime()) );
             logger.debug("uploadFile ends");
       		
         }  else {
    	 
        	 logger.error("Duplicate File");
       		 model.addAttribute("duplicateFile", true);
         }
        return "uploadStatus";
    }

   
    @RequestMapping(value = "/displayRecords", method = RequestMethod.POST)
    public String displayRecords(@RequestParam("filename") String filename,
                                  ModelMap model) {
    	
    	 logger.debug("displayRecords starts");
    	List<ExchangeRecord>  records =  cservice.findRecordsByFilename(filename);
    	 logger.debug("records" + records);
    	List<InvalidExchangeRecord>  invalidrecords =  ixrservice.findInvalidRecordsByFilename(filename);
    	 logger.debug("invalidrecords" + invalidrecords);
    	  model.addAttribute("records", records);
    	  model.addAttribute("invalidrecords", invalidrecords);
    	 logger.debug("displayRecords ends");
    	 return "displayResults";
    }
 
    @RequestMapping(value = { "/uploadStatus" }, method = RequestMethod.GET)
    public String uploadStatus() {
    	 logger.debug("uploadStatus called");
        return "uploadStatus";
    }

   
}