package com.americanexpress.Eligibility.businessobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Applicant {
	private String firstName;
	private String lastName;
	private long ssn;
	
	public Applicant() {}
	
	public Applicant(String firstName, String lastName, long ssn ) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getSsn() {
		return ssn;
	}
	public void setSsn(long ssn) {
		this.ssn = ssn;
	}

}
