package com.clustereddatawarehouse.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="InvalidExchangeRecord")
public class InvalidExchangeRecord {
	
	@EmbeddedId
	private InvalidExchangeRecordPK erpk;
    
	
    @Column(name = "recordtext")
    private String recordtext;


	public InvalidExchangeRecordPK getErpk() {
		return erpk;
	}


	public void setErpk(InvalidExchangeRecordPK erpk) {
		this.erpk = erpk;
	}


	public String getRecordtext() {
		return recordtext;
	}


	public void setRecordtext(String recordtext) {
		this.recordtext = recordtext;
	}


    


}

