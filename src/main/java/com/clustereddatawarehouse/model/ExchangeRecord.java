package com.clustereddatawarehouse.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="ExchangeRecords")
public class ExchangeRecord {
	
//	@EmbeddedId
//	private ExchangeRecordPK erpk;
	
	@Id
	private String dealId;
	

	@Size(min=3, max=50)
    @Column(name = "FILENAME", nullable = false)
	private String filename;

    
    @Size(min=3, max=3)
    @Column(name = "FROMCURRENCY", nullable = false)
    private String fromcurrency;
    
    @Size(min=3, max=3)
    @Column(name = "TOCURRENCY", nullable = false)
    private String tocurrency;
 
    @NotNull
    @Column(name = "DEALTIME", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dealtime;
 
    @NotNull
    @Digits(integer=8, fraction=2)
    @Column(name = "DEALAMOUNT", nullable = false)
    private BigDecimal dealamount;



	public String getFromcurrency() {
		return fromcurrency;
	}

	public void setFromcurrency(String fromcurrency) {
		this.fromcurrency = fromcurrency;
	}

	public String getTocurrency() {
		return tocurrency;
	}

	public void setTocurrency(String tocurrency) {
		this.tocurrency = tocurrency;
	}

	public Date getDealtime() {
		return dealtime;
	}

	public void setDealtime(Date dealtime) {
		this.dealtime = dealtime;
	}

	public BigDecimal getDealamount() {
		return dealamount;
	}

	public void setDealamount(BigDecimal dealamount) {
		this.dealamount = dealamount;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExchangeRecord other = (ExchangeRecord) obj;
		if (dealId == null) {
			if (other.dealId != null)
				return false;
		} else if (!dealId.equals(other.dealId))
			return false;
		if (dealamount == null) {
			if (other.dealamount != null)
				return false;
		} else if (!dealamount.equals(other.dealamount))
			return false;
		if (dealtime == null) {
			if (other.dealtime != null)
				return false;
		} else if (!dealtime.equals(other.dealtime))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		if (fromcurrency == null) {
			if (other.fromcurrency != null)
				return false;
		} else if (!fromcurrency.equals(other.fromcurrency))
			return false;
		if (tocurrency == null) {
			if (other.tocurrency != null)
				return false;
		} else if (!tocurrency.equals(other.tocurrency))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExchangeRecord [dealId=" + dealId + ", filename=" + filename + ", fromcurrency=" + fromcurrency
				+ ", tocurrency=" + tocurrency + ", dealtime=" + dealtime + ", dealamount=" + dealamount + "]";
	}

	

	
     
   


}

