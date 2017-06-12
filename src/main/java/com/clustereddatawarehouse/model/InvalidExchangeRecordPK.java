package com.clustereddatawarehouse.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class InvalidExchangeRecordPK implements Serializable {
	
	@Column(name = "ID", nullable = false)	
	private Integer id;
	
	@Size(min=3, max=50)
    @Column(name = "FILENAME", nullable = false)
	private String filename;

	    public InvalidExchangeRecordPK() {}

	    public InvalidExchangeRecordPK(Integer id, String filename) {
	        this.id = id;
	        this.filename = filename;
	    }

	    
	    
	    
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((filename == null) ? 0 : filename.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			InvalidExchangeRecordPK other = (InvalidExchangeRecordPK) obj;
			if (filename == null) {
				if (other.filename != null)
					return false;
			} else if (!filename.equals(other.filename))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
	   
	    
}



