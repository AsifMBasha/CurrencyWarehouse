package com.currency;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.clustereddatawarehouse.controller.UploadController; 
import com.clustereddatawarehouse.model.ExchangeRecord;
import com.clustereddatawarehouse.model.InvalidExchangeRecordPK;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UploadController.class})
@WebAppConfiguration


public class UploadControllerTest {  

	
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    
    
    @Test
    public void testUploadFile() throws Exception {
    	
    	InputStream inputStream = this.getClass().getClassLoader()
    		    .getResourceAsStream("resources/1testfile.txt");
    	
    	
    	 MockMultipartFile textfile = new MockMultipartFile("file", "1testfile.txt", "text/plain", inputStream);
        
        mockMvc.perform(
        		MockMvcRequestBuilders.fileUpload("/upload").file(textfile)).andExpect(status().is(200));
    	 
    }
    
    @Test
    public void testdisplayRecordssucess() throws Exception {
    	
    	        String filename="903testfile.txt";
    	 MockMultipartFile textfile = new MockMultipartFile("file", filename, "text/plain", "903,INR,USD,2017-02-02 09:23:39,3500".getBytes());
         
         mockMvc.perform(
         		MockMvcRequestBuilders.fileUpload("/upload").file(textfile)).andExpect(status().is(200));
         
        List<ExchangeRecord>  records =  new ArrayList<ExchangeRecord>();
        ExchangeRecord er = new ExchangeRecord();
        er.setDealamount(new BigDecimal("3500"));
        Date date = null; SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    date = sdf.parse("2017-02-02 09:23:39");
        er.setDealtime(date);
        er.setDealId("903");
        er.setFromcurrency("INR"); 
        er.setTocurrency("USD"); 
        er.setFilename(filename);

        records.add(er);
        
        mockMvc.perform(
        		MockMvcRequestBuilders.post("/displayRecords").param("filename",filename))
        			.andExpect(model().attribute("records",records));
    }
    
    @Test
    public void testuploadDuplicateFile() throws Exception {
    	  
    	        String filename="904testfile.txt";
    	 MockMultipartFile textfile = new MockMultipartFile("file", filename, "text/plain", "904,INR,USD,2017-02-02 09:23:39,3500".getBytes());
         
         mockMvc.perform(
         		MockMvcRequestBuilders.fileUpload("/upload").file(textfile)).andExpect(status().is(200));
           
      
        String filename2="904testfile.txt";
   	 MockMultipartFile textfile2 = new MockMultipartFile("file", filename2, "text/plain", "904,INR,USD,2017-02-02 09:23:39,3500".getBytes());
        
        mockMvc.perform(
        		MockMvcRequestBuilders.fileUpload("/upload").file(textfile2)).andExpect(model().attribute("duplicateFile",true));
        
       
    } 
    
    @Test
    public void testuploadDuplicateDealId() throws Exception {
    	  
    	        String filename="905testfile.txt";
    	 MockMultipartFile textfile = new MockMultipartFile("file", filename, "text/plain", "xxxxx,INR,USD,2017-02-02 09:23:39,3500".getBytes());
         
         mockMvc.perform(
         		MockMvcRequestBuilders.fileUpload("/upload").file(textfile)).andExpect(status().is(200));
         
      
        String filename2="906testfile.txt";
   	 MockMultipartFile textfile2 = new MockMultipartFile("file", filename2, "text/plain", "xxxxx,INR,USD,2017-02-02 09:23:39,3500".getBytes());
        
        mockMvc.perform(
        		MockMvcRequestBuilders.fileUpload("/upload").file(textfile2)).andExpect(model().attribute("duplicatedeal",true));
        
       
    }
    
    @Test
    public void testuploadStatus() throws Exception {
    	      
       mockMvc.perform(
       		MockMvcRequestBuilders.get("/uploadStatus"))
				.andExpect(status().isOk()).andExpect(view().name("uploadStatus"));
   	 
   }
    
    @Test
    public void testUploadScreen() throws Exception {
	      
        mockMvc.perform(
        		MockMvcRequestBuilders.get("/uploadScreen"))
 				.andExpect(status().isOk()).andExpect(view().name("upload"));
    	 
    }
    
    @Test
    public void testfindRecordsFunction() throws Exception {
	      
        mockMvc.perform(
        		MockMvcRequestBuilders.get("/findRecords"))
 				.andExpect(status().isOk()).andExpect(view().name("findRecords"));
    	 
    }
    
    @Test
    public void testGoHome() throws Exception {
	      
        mockMvc.perform(
        		MockMvcRequestBuilders.get("/home"))
 				.andExpect(status().isOk()).andExpect(view().name("index"));
    	 
    }
    
    
    
}