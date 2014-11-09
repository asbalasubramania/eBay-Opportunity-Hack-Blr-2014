package com.sevaikarangal.blooddonationapp.bean;

import java.util.Date;

public class DonorRequest {
	
	private Long donorId;
	private String email;
	private String name;
	private Long phoneNumber;
	private String city;
	private String State;
	private String bloodGroup;
	private Date lastDonatedDate;
	private Date optOutUntilDate;
	private double latitude;
	private double longitude;
	private String locality;
	private Date createdDate;
	private Date lastModifiedDate;
	private String gender;
	private double height;
	private double weight;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Long getDonorId() {
		return donorId;
	}

	public void setDonorId(Long donorId) {
		this.donorId = donorId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public Date getLastDonatedDate() {
		return lastDonatedDate;
	}

	public void setLastDonatedDate(Date lastDonatedDate) {
		this.lastDonatedDate = lastDonatedDate;
	}

	public Date getOptOutUntilDate() {
		return optOutUntilDate;
	}

	public void setOptOutUntilDate(Date optOutUntilDate) {
		this.optOutUntilDate = optOutUntilDate;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(email !=null) sb.append("\"email\":\""+this.email).append("\",");
		if(name !=null) sb.append("\"name\":\""+this.name).append("\",");
		if(phoneNumber !=null) sb.append("\"phoneNumber\":\""+this.phoneNumber).append("\",");
		if(city !=null) sb.append("\"city\":\""+this.city).append("\",");
		if(State !=null) sb.append("\"State\":\""+this.State).append("\",");
		if(bloodGroup !=null) sb.append("\"bloodGroup\":\""+this.bloodGroup).append("\",");
		if(lastDonatedDate != null) sb.append("\"lastDonatedDate"+this.lastDonatedDate).append("\",");
		if(optOutUntilDate != null) sb.append("\"optOutUntilDate\":\""+this.optOutUntilDate).append("\",");
		if(latitude != -1) sb.append("\"latitude\":\""+this.latitude).append("\",");
		if(locality != null) sb.append("\"locality\":\""+this.locality).append("\",");
		if(longitude != -1) sb.append("\"longitude\":\""+this.longitude).append("\",");
		if(gender != null) sb.append("\"gender\":\""+this.gender).append("\",");
		if(height != -1) sb.append("\"height\":\""+this.height).append("\",");
		if(weight != -1) sb.append("\"weight\":\""+this.weight).append("\",");
		String returnStr = sb.substring(0,sb.length()-1);
		return "{" + returnStr + "}";
	}	

}
