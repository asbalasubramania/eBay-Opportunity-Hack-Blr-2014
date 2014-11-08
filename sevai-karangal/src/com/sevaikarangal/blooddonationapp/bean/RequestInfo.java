package com.sevaikarangal.blooddonationapp.bean;

import java.util.Date;

public class RequestInfo {
	private Long requestId;
	private String bloodGroup;
	private String patientName;
	private String hospital;
	private String contactPerson;
	private Long contactNumber;
	private String locality;
	private String city;
	private Date requestDate;
	private int status = -1;
	private int bloodUnits = -1;
	
	public RequestInfo() {
		
	}
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public Long getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getBloodUnits() {
		return bloodUnits;
	}
	public void setBloodUnits(int bloodUnits) {
		this.bloodUnits = bloodUnits;
	}
	
	public String getinfoinstr()
	{
		StringBuffer strbuf = new StringBuffer();
		
		strbuf.append("Blood Type: " + this.bloodGroup + System.getProperty("line.separator") +
				"UNits:" + this.bloodUnits + System.getProperty("line.separator") +
				"At this location " + this.locality + System.getProperty("line.separator") +
				"Contact No " + Long.toString((this.contactNumber)));
		
		
		return strbuf.toString();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(bloodGroup !=null) sb.append("\"bloodGroup\":\""+this.bloodGroup).append("\",");
		if(hospital !=null) sb.append("\"hospital\":\""+this.hospital).append("\",");
		if(contactPerson !=null) sb.append("\"contactPerson\":\""+this.contactPerson).append("\",");
		if(contactNumber !=null) sb.append("\"contactNumber\":\""+this.contactNumber).append("\",");
		if(locality !=null) sb.append("\"locality\":\""+this.locality).append("\",");
		if(city !=null) sb.append("\"city\":\""+this.city).append("\",");
		if(status != -1l) sb.append("\"status"+this.status).append("\",");
		if(bloodUnits != -1) sb.append("\"bloodUnits\":\""+this.bloodUnits).append("\",");
		String returnStr = sb.substring(0,sb.length()-1);
		return "{" + returnStr + "}";
	}
}