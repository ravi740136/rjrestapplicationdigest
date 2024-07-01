package rj.training.rest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {
	
	public Address() {
		
	}
	
	public String getHno() {
		return hno;
	}

	public void setHno(String hno) {
		this.hno = hno;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	
	String hno;
	
	
	String street;
	
	@Column
	String city;
	
	 @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((hno == null) ? 0 : hno.hashCode());
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
	        Address other = (Address) obj;
	        if (id == null) {
	            if (other.id != null)
	                return false;
	        } else if (!id.equals(other.id))
	            return false;
	        return true;
	    }
	    
	    @Override
	    public String toString() {
	    	// TODO Auto-generated method stub
	    	return String.format("Address[hno='%s', street='%s', city='%s']", hno, street, city);
	    }
}
